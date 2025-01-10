package com.example.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/associations")
public class AssociationController {

    private final Map<String, List<String>> roomToWindowsMap = new HashMap<>();
    private final Map<String, List<String>> roomToSensorsMap = new HashMap<>();

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
        roomToSensorsMap.remove(roomName); // Supprimer également les capteurs associés
        return ResponseEntity.ok("Room deleted successfully");
    }

    @DeleteMapping("/windows/{windowName}")
    public ResponseEntity<String> deleteWindow(@PathVariable String windowName) {
        roomToWindowsMap.forEach((room, windows) -> windows.remove(windowName));
        return ResponseEntity.ok("Window and its associations deleted successfully");
    }

    @PutMapping("/rooms/{oldName}")
    public ResponseEntity<String> updateRoomName(@PathVariable String oldName, @RequestBody Map<String, String> request) {
        String newName = request.get("name");

        if (newName == null || !roomToWindowsMap.containsKey(oldName)) {
            return ResponseEntity.badRequest().body("Invalid room name or room does not exist");
        }

        // Mettre à jour les fenêtres associées
        List<String> windows = roomToWindowsMap.remove(oldName);
        roomToWindowsMap.put(newName, windows);

        // Mettre à jour les capteurs associés
        List<String> sensors = roomToSensorsMap.remove(oldName);
        roomToSensorsMap.put(newName, sensors);

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

        return ResponseEntity.ok("Window name updated successfully");
    }

    // === ASSOCIATION DES CAPTEURS AUX SALLES ===

    @PostMapping("/sensors")
    public ResponseEntity<String> associateSensorToRoomByPosition(@RequestBody Map<String, String> request) {
        String roomName = request.get("roomName");
        String sensorPosition = request.get("sensorPosition");

        if (roomName == null || sensorPosition == null) {
            return ResponseEntity.badRequest().body("roomName and sensorPosition are required");
        }

        roomToSensorsMap.computeIfAbsent(roomName, k -> new ArrayList<>()).add(sensorPosition);
        return ResponseEntity.ok("Sensor associated to room by position successfully");
    }

    @DeleteMapping("/sensors/{sensorPosition}")
    public ResponseEntity<String> deleteSensorAssociation(@PathVariable String sensorPosition) {
        roomToSensorsMap.forEach((room, sensors) -> sensors.remove(sensorPosition));
        return ResponseEntity.ok("Sensor association removed successfully");
    }

    // === RECUPÉRER LES ASSOCIATIONS ===

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAssociations() {
        List<Map<String, Object>> result = new ArrayList<>();

        roomToWindowsMap.forEach((roomName, windows) -> {
            List<Map<String, Object>> windowDetails = new ArrayList<>();
            windows.forEach(window -> windowDetails.add(Map.of("name", window)));

            List<String> sensors = roomToSensorsMap.getOrDefault(roomName, new ArrayList<>());

            Map<String, Object> assoc = new HashMap<>();
            assoc.put("room", Map.of("name", roomName));
            assoc.put("windows", windowDetails);
            assoc.put("sensors", sensors.stream().map(position -> Map.of("position", position)).toList());
            result.add(assoc);
        });

        return ResponseEntity.ok(result);
    }
}



