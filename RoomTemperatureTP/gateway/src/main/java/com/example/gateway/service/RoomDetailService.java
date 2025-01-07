package com.example.gateway.service;

import com.example.gateway.model.Room;
import com.example.gateway.model.Window;
import com.example.gateway.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomDetailService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Obtenir les détails consolidés des rooms
    public List<Map<String, Object>> getAllRoomDetails() {
        List<Room> rooms = webClientBuilder.build()
                .get()
                .uri("http://room-service/rooms")
                .retrieve()
                .bodyToFlux(Room.class)
                .collectList()
                .block();

        List<Map<String, Object>> detailedRooms = new ArrayList<>();

        for (Room room : rooms) {
            Map<String, Object> details = new HashMap<>();
            details.put("id", room.getId());
            details.put("name", room.getName());

            // Récupérer les fenêtres associées
            List<Window> windows = webClientBuilder.build()
                    .get()
                    .uri("http://window-service/windows?roomId=" + room.getId())
                    .retrieve()
                    .bodyToFlux(Window.class)
                    .collectList()
                    .block();

            details.put("windowsCount", windows.size());

            // Compter les capteurs
            int sensorCount = 0;
            for (Window window : windows) {
                List<Sensor> sensors = webClientBuilder.build()
                        .get()
                        .uri("http://sensor-service/sensors?windowId=" + window.getId())
                        .retrieve()
                        .bodyToFlux(Sensor.class)
                        .collectList()
                        .block();

                sensorCount += sensors.size();
            }

            details.put("sensorsCount", sensorCount);
            detailedRooms.add(details);
        }

        return detailedRooms;
    }

    // Ajouter une room
    public String addRoom(Map<String, String> roomData) {
        return webClientBuilder.build()
                .post()
                .uri("http://room-service/rooms")
                .bodyValue(roomData)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // Ajouter une fenêtre à une room
    public String addWindowToRoom(Long roomId, Map<String, String> windowData) {
        windowData.put("roomId", roomId.toString());
        return webClientBuilder.build()
                .post()
                .uri("http://window-service/windows")
                .bodyValue(windowData)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // Ajouter un capteur à une fenêtre
    public String addSensorToWindow(Long windowId, Map<String, Object> sensorData) {
        sensorData.put("windowId", windowId);
        return webClientBuilder.build()
                .post()
                .uri("http://sensor-service/sensors/add")
                .bodyValue(sensorData)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
