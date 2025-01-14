package com.example.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    private final AssociationController associationController;
    private final RestTemplate restTemplate;

    private final String SENSOR_SERVICE_URL = "http://localhost:8081/sensors";
    private final String WINDOW_SERVICE_URL = "http://localhost:8082/windows";

    public TemperatureController(AssociationController associationController, RestTemplate restTemplate) {
        this.associationController = associationController;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void automateWindowControl() {
        List<Map<String, Object>> associations = fetchAllAssociations();

        for (Map<String, Object> room : associations) {
            Map<String, Object> roomDetails = (Map<String, Object>) room.get("room");
            Long roomId = (Long) roomDetails.get("id");

            List<Map<String, Object>> windows = (List<Map<String, Object>>) room.get("windows");
            double averageTemperature = fetchAverageRoomTemperature(roomId);

            if (averageTemperature > 25.0) {
                openWindows(windows);
            } else if (averageTemperature < 22.0) {
                closeWindows(windows);
            }
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<Map<String, Object>>> getRoomStatus() {
        List<Map<String, Object>> associations = fetchAllAssociations();
        List<Map<String, Object>> statusList = new ArrayList<>();

        for (Map<String, Object> room : associations) {
            Map<String, Object> roomDetails = (Map<String, Object>) room.get("room");
            Long roomId = (Long) roomDetails.get("id");

            List<Map<String, Object>> windows = (List<Map<String, Object>>) room.get("windows");
            double averageTemperature = fetchAverageRoomTemperature(roomId);

            List<Map<String, Object>> windowStates = new ArrayList<>();
            for (Map<String, Object> window : windows) {
                Long windowId = (Long) window.get("id");
                String state = fetchWindowState(windowId);
                windowStates.add(Map.of("id", windowId, "state", state));
            }

            statusList.add(Map.of(
                    "roomId", roomId,
                    "temperature", averageTemperature,
                    "windows", windowStates));
        }

        return ResponseEntity.ok(statusList);
    }

    private List<Map<String, Object>> fetchAllAssociations() {
        ResponseEntity<List<Map<String, Object>>> response = associationController.getAssociations();
        return response.getBody();
    }

    private double fetchAverageRoomTemperature(Long roomId) {
        ResponseEntity<Map<String, Object>> response = associationController.getAssociationsByRoomId(roomId);
        Map<String, Object> associations = response.getBody();

        if (associations == null) {
            return 0.0;
        }

        List<Map<String, Object>> windows = (List<Map<String, Object>>) associations.get("windows");
        List<Long> sensorIds = new ArrayList<>();

        for (Map<String, Object> window : windows) {
            List<Map<String, Object>> sensors = (List<Map<String, Object>>) window.get("sensors");
            for (Map<String, Object> sensor : sensors) {
                sensorIds.add((Long) sensor.get("id"));
            }
        }

        double totalTemperature = 0.0;
        int count = 0;

        for (Long sensorId : sensorIds) {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(SENSOR_SERVICE_URL + "/get/" + sensorId,
                    Map.class);
            Map<String, Object> sensorData = responseEntity.getBody();

            if (sensorData != null && sensorData.containsKey("temperature")) {
                Double sensorTemperature = (Double) sensorData.get("temperature");
                if (sensorTemperature != null) {
                    totalTemperature += sensorTemperature;
                    count++;
                }
            }
        }

        return count > 0 ? totalTemperature / count : 0.0;
    }

    private String fetchWindowState(Long windowId) {
        ResponseEntity<Map> response = restTemplate.getForEntity(WINDOW_SERVICE_URL + "/" + windowId, Map.class);
        return (String) response.getBody().get("windowState");
    }

    private void openWindows(List<Map<String, Object>> windows) {
        for (Map<String, Object> window : windows) {
            Long windowId = (Long) window.get("id");
            restTemplate.put(WINDOW_SERVICE_URL + "/" + windowId, Map.of("windowState", "open"));
        }
    }

    private void closeWindows(List<Map<String, Object>> windows) {
        for (Map<String, Object> window : windows) {
            Long windowId = (Long) window.get("id");
            restTemplate.put(WINDOW_SERVICE_URL + "/" + windowId, Map.of("windowState", "closed"));
        }
    }
}
