package com.example.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/associations")
public class AssociationController {

    private final Map<Long, List<Long>> roomToWindowsMap = new HashMap<>();
    private final Map<Long, List<Long>> windowToSensorsMap = new HashMap<>();

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
                windowToSensorsMap.remove(windowId);
            }
        }

        return ResponseEntity.ok("Room and its associations deleted successfully");
    }

    @DeleteMapping("/windows/{windowId}")
    public ResponseEntity<String> deleteWindow(@PathVariable Long windowId) {
        roomToWindowsMap.forEach((room, windows) -> windows.remove(windowId));
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
   
}
