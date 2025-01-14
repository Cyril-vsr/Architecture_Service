package com.example.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/associations")
public class AssociationController {

    private final Map<Long, List<Long>> roomToWindowsMap = new HashMap<>();
    private final Map<Long, List<Long>> windowToSensorsMap = new HashMap<>();
    private final Map<Long, Double> sensorValues = new HashMap<>(); // Mock for sensor values
    private final Map<Long, String> windowStates = new HashMap<>(); // Track window states

    // === ASSOCIATION DES FENÊTRES AUX SALLES ===

    @PostMapping("/windows")
    public ResponseEntity<String> associateWindowToRoom(@RequestBody Map<String, Long> request) {
        Long roomId = request.get("roomId");
        Long windowId = request.get("windowId");

        if (roomId == null || windowId == null) {
            return ResponseEntity.badRequest().body("roomId and windowId are required");
        }

        roomToWindowsMap.computeIfAbsent(roomId, k -> new ArrayList<>()).add(windowId);
        return ResponseEntity.ok("Window associated to room successfully");
    }

    @PostMapping("/sensors")
    public ResponseEntity<String> associateSensorToRoom(@RequestBody Map<String, Long> request) {
        Long roomId = request.get("roomId");
        Long sensorId = request.get("sensorId");

        if (roomId == null || sensorId == null) {
            return ResponseEntity.badRequest().body("roomId and sensorId are required");
        }

        windowToSensorsMap.computeIfAbsent(roomId, k -> new ArrayList<>()).add(sensorId);
        return ResponseEntity.ok("Sensor associated to room successfully");
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        if (!roomToWindowsMap.containsKey(roomId)) {
            return ResponseEntity.badRequest().body("Room does not exist");
        }

        List<Long> windows = roomToWindowsMap.remove(roomId);
        if (windows != null) {
            for (Long windowId : windows) {
                windowStates.remove(windowId);
                windowToSensorsMap.remove(windowId);
            }
        }

        return ResponseEntity.ok("Room and its associations deleted successfully");
    }

    @DeleteMapping("/windows/{windowId}")
    public ResponseEntity<String> deleteWindow(@PathVariable Long windowId) {
        roomToWindowsMap.forEach((room, windows) -> windows.remove(windowId));
        windowStates.remove(windowId);
        windowToSensorsMap.remove(windowId);
        return ResponseEntity.ok("Window and its associations deleted successfully");
    }

    // === RECUPÉRER LES ASSOCIATIONS ===

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAssociations() {
        List<Map<String, Object>> result = new ArrayList<>();

        roomToWindowsMap.forEach((roomId, windows) -> {
            List<Map<String, Object>> windowDetails = new ArrayList<>();
            windows.forEach(windowId -> {
                List<Long> sensors = windowToSensorsMap.getOrDefault(roomId, new ArrayList<>());
                Map<String, Object> windowInfo = new HashMap<>();
                windowInfo.put("id", windowId);
                windowInfo.put("state", windowStates.get(windowId));
                windowInfo.put("sensors", sensors.stream().map(id -> Map.of("id", id)).toList());
                windowDetails.add(windowInfo);
            });

            Map<String, Object> assoc = new HashMap<>();
            assoc.put("room", Map.of("id", roomId));
            assoc.put("windows", windowDetails);
            result.add(assoc);
        });

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Map<String, Object>> getAssociationsByRoomId(@PathVariable Long roomId) {
        List<Long> windows = roomToWindowsMap.getOrDefault(roomId, new ArrayList<>());

        List<Map<String, Object>> windowDetails = new ArrayList<>();
        for (Long windowId : windows) {
            List<Long> sensors = windowToSensorsMap.getOrDefault(roomId, new ArrayList<>());
            Map<String, Object> windowInfo = new HashMap<>();
            windowInfo.put("id", windowId);
            windowInfo.put("sensors", sensors.stream().map(id -> Map.of("id", id)).toList());
            windowDetails.add(windowInfo);
        }

        Map<String, Object> assoc = new HashMap<>();
        assoc.put("room", Map.of("id", roomId));
        assoc.put("windows", windowDetails);

        return ResponseEntity.ok(assoc);
    }

    // === GESTION AUTOMATIQUE DES FENÊTRES ===
    @Scheduled(fixedRate = 10000)
    public void manageWindowStates() {
        roomToWindowsMap.forEach((roomId, windows) -> {
            List<Long> sensors = windowToSensorsMap.getOrDefault(roomId, new ArrayList<>());

            if (sensors.isEmpty()) {
                return; // Pas de capteurs associés, garder l'état des fenêtres inchangé
            }

            // Calculer la moyenne des valeurs des capteurs
            double averageTemperature = sensors.stream()
                    .mapToDouble(sensorId -> sensorValues.getOrDefault(sensorId, Double.NaN))
                    .filter(value -> !Double.isNaN(value))
                    .average()
                    .orElse(Double.NaN);

                    if (Double.isNaN(averageTemperature)) {
                        System.out.println("No valid sensor data for room: " + roomId);
                        return; // Ne pas modifier l'état des fenêtres
                    }
                    

            // Ajuster l'état des fenêtres en fonction de la température moyenne
            String newState = null;
            if (averageTemperature < 20) {
                newState = "CLOSED";
            } else if (averageTemperature > 21) {
                newState = "OPEN";
            }

            if (newState != null) {
                for (Long windowId : windows) {
                    windowStates.put(windowId, newState);
                }
            }
        });
    }

    @PutMapping("/windows/{id}/state")
    public ResponseEntity<String> updateWindowState(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newState = request.get("state");
        if (newState == null || (!newState.equals("OPEN") && !newState.equals("CLOSED"))) {
            return ResponseEntity.badRequest().body("Invalid state");
        }

        if (!windowStates.containsKey(id)) {
            return ResponseEntity.badRequest().body("Window not found");
        }

        windowStates.put(id, newState);
        return ResponseEntity.ok("Window state updated successfully");
    }

    @PostMapping("/sensors/data")
    public ResponseEntity<String> updateSensorValue(@RequestBody Map<String, Object> request) {
        Long sensorId = ((Number) request.get("sensorId")).longValue();
        Double value = ((Number) request.get("value")).doubleValue();

        if (sensorId == null || value == null) {
            return ResponseEntity.badRequest().body("sensorId and value are required");
        }

        sensorValues.put(sensorId, value);
        return ResponseEntity.ok("Sensor value updated successfully");
    }
}
