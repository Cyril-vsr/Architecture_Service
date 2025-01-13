package com.example.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    @Autowired
    private RestTemplate restTemplate;

    private final String ROOM_SERVICE_URL = "http://localhost:8083/rooms";
    private final String WINDOW_SERVICE_URL = "http://localhost:8082/windows";
    private final String SENSOR_SERVICE_URL = "http://localhost:8081/sensors";
    private final String ASSOCIATION_SERVICE_URL = "http://localhost:8080/associations";

    @Scheduled(fixedRate = 5000) // Every 5 seconds
    public void automateWindowControl() {
        List<Long> roomIds = fetchAllRoomIds();

        for (Long roomId : roomIds) {
            Map<String, Object> associations = fetchAssociationsByRoomId(roomId);
            double temperature = fetchRoomTemperature(associations);
            List<Long> windowIds = fetchRoomWindows(associations);

            if (temperature > 25.0) {
                openWindows(windowIds);
            } else if (temperature < 22.0) {
                closeWindows(windowIds);
            }
        }
    }

    private List<Long> fetchAllRoomIds() {
        ResponseEntity<List<Long>> response = restTemplate.exchange(
            ROOM_SERVICE_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Long>>() {}
        );
        return response.getBody();
    }

    private Map<String, Object> fetchAssociationsByRoomId(Long roomId) {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            ASSOCIATION_SERVICE_URL + "/" + roomId,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        return response.getBody();
    }

    private double fetchRoomTemperature(Map<String, Object> associations) {
        List<Long> sensorIds = (List<Long>) associations.get("sensors");
        double totalTemperature = 0.0;
        int sensorCount = 0;

        for (Long sensorId : sensorIds) {
            ResponseEntity<Double> response = restTemplate.exchange(
                SENSOR_SERVICE_URL + "/get/" + sensorId,
                HttpMethod.GET,
                null,
                Double.class
            );
            totalTemperature += response.getBody();
            sensorCount++;
        }

        return sensorCount > 0 ? totalTemperature / sensorCount : 0.0;
    }

    private List<Long> fetchRoomWindows(Map<String, Object> associations) {
        return (List<Long>) associations.get("windows");
    }

    private void openWindows(List<Long> windowIds) {
        for (Long windowId : windowIds) {
            restTemplate.exchange(
                WINDOW_SERVICE_URL + "/" + windowId,
                HttpMethod.PUT,
                new HttpEntity<>(Map.of("windowState", "open")),
                Void.class
            );
        }
    }

    private void closeWindows(List<Long> windowIds) {
        for (Long windowId : windowIds) {
            restTemplate.exchange(
                WINDOW_SERVICE_URL + "/" + windowId,
                HttpMethod.PUT,
                new HttpEntity<>(Map.of("windowState", "closed")),
                Void.class
            );
        }
    }
}