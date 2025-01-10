package com.example.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/associations")
public class AssociationController {

    private final Map<String, List<String>> roomToWindowsMap = new HashMap<>();
    private final Map<String, List<String>> windowToSensorsMap = new HashMap<>();

    // === ASSOCIATION DES FENÊTRES AUX SALLES ===

    @PostMapping
    public ResponseEntity<String> associateWindowToRoom(@RequestBody Map<String, String> request) {
        String roomName = request.get("roomName");
        String windowName = request.get("windowName");

        if (roomName == null || windowName == null) {
            return ResponseEntity.badRequest().body("roomName and windowName are required");
        }

        roomToWindowsMap.computeIfAbsent(roomName, k -> new ArrayList<>()).add(windowName);
        return ResponseEntity.ok("Window associated to room successfully");
    }

    @DeleteMapping("/rooms/{roomName}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomName) {
        if (!roomToWindowsMap.containsKey(roomName)) {
            return ResponseEntity.badRequest().body("Room does not exist");
        }
        roomToWindowsMap.remove(roomName);
        return ResponseEntity.ok("Room deleted successfully");
    }

    @DeleteMapping("/windows/{windowName}")
    public ResponseEntity<String> deleteWindow(@PathVariable String windowName) {
        roomToWindowsMap.forEach((room, windows) -> windows.remove(windowName));
        windowToSensorsMap.remove(windowName);
        return ResponseEntity.ok("Window and its associations deleted successfully");
    }

    @PutMapping("/rooms/{oldName}")
    public ResponseEntity<String> updateRoomName(@PathVariable String oldName, @RequestBody Map<String, String> request) {
        String newName = request.get("name");

        if (newName == null || !roomToWindowsMap.containsKey(oldName)) {
            return ResponseEntity.badRequest().body("Invalid room name or room does not exist");
        }

        List<String> windows = roomToWindowsMap.remove(oldName);
        roomToWindowsMap.put(newName, windows);
        return ResponseEntity.ok("Room name updated successfully");
    }

    @PutMapping("/windows/{oldName}")
    public ResponseEntity<String> updateWindowName(@PathVariable String oldName, @RequestBody Map<String, String> request) {
        String newName = request.get("name");

        if (newName == null) {
            return ResponseEntity.badRequest().body("Invalid window name");
        }

        roomToWindowsMap.forEach((room, windows) -> {
            if (windows.contains(oldName)) {
                windows.remove(oldName);
                windows.add(newName);
            }
        });

        if (windowToSensorsMap.containsKey(oldName)) {
            List<String> sensors = windowToSensorsMap.remove(oldName);
            windowToSensorsMap.put(newName, sensors);
        }

        return ResponseEntity.ok("Window name updated successfully");
    }

    // === ASSOCIATION DES CAPTEURS AUX FENÊTRES ===

    @PostMapping("/sensors")
    public ResponseEntity<String> associateSensorToWindow(@RequestBody Map<String, String> request) {
        String windowName = request.get("windowName");
        String sensorName = request.get("sensorName");

        if (windowName == null || sensorName == null) {
            return ResponseEntity.badRequest().body("windowName and sensorName are required");
        }

        windowToSensorsMap.computeIfAbsent(windowName, k -> new ArrayList<>()).add(sensorName);
        return ResponseEntity.ok("Sensor associated to window successfully");
    }

    @DeleteMapping("/sensors/{sensorName}")
    public ResponseEntity<String> deleteSensor(@PathVariable String sensorName) {
        windowToSensorsMap.forEach((window, sensors) -> sensors.remove(sensorName));
        return ResponseEntity.ok("Sensor removed from associations successfully");
    }

    @PutMapping("/sensors/{oldName}")
    public ResponseEntity<String> updateSensorName(@PathVariable String oldName, @RequestBody Map<String, String> request) {
        String newName = request.get("name");

        if (newName == null) {
            return ResponseEntity.badRequest().body("Invalid sensor name");
        }

        windowToSensorsMap.forEach((window, sensors) -> {
            if (sensors.contains(oldName)) {
                sensors.remove(oldName);
                sensors.add(newName);
            }
        });

        return ResponseEntity.ok("Sensor name updated successfully");
    }

    // === RECUPÉRER LES ASSOCIATIONS ===

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAssociations() {
        List<Map<String, Object>> result = new ArrayList<>();

        roomToWindowsMap.forEach((roomName, windows) -> {
            List<Map<String, Object>> windowDetails = new ArrayList<>();
            windows.forEach(window -> {
                List<String> sensors = windowToSensorsMap.getOrDefault(window, new ArrayList<>());
                Map<String, Object> windowInfo = new HashMap<>();
                windowInfo.put("name", window);
                windowInfo.put("sensors", sensors.stream().map(name -> Map.of("name", name)).toList());
                windowDetails.add(windowInfo);
            });

            Map<String, Object> assoc = new HashMap<>();
            assoc.put("room", Map.of("name", roomName));
            assoc.put("windows", windowDetails);
            result.add(assoc);
        });

        return ResponseEntity.ok(result);
    }
}


