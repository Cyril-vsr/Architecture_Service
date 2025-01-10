package com.example.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/associations")
public class AssociationController {

    private final Map<String, List<String>> roomToWindowsMap = new HashMap<>();

    // Associer une fenêtre à une salle
    @PostMapping
    public ResponseEntity<String> associateWindowToRoom(@RequestBody Map<String, String> request) {
        String roomName = request.get("roomName");
        String windowName = request.get("windowName");

        if (roomName == null || windowName == null) {
            return ResponseEntity.badRequest().body("roomName and windowName are required");
        }

        // Ajouter l'association
        roomToWindowsMap.computeIfAbsent(roomName, k -> new ArrayList<>()).add(windowName);
        return ResponseEntity.ok("Associated successfully");
    }

    // Récupérer toutes les associations
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAssociations() {
        List<Map<String, Object>> result = new ArrayList<>();

        roomToWindowsMap.forEach((roomName, windows) -> {
            Map<String, Object> assoc = new HashMap<>();
            assoc.put("room", Map.of("name", roomName));
            assoc.put("windows", windows.stream().map(name -> Map.of("name", name)).toList());
            result.add(assoc);
        });

        return ResponseEntity.ok(result);
    }

    // Supprimer une salle et ses associations
    @DeleteMapping("/rooms/{roomName}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomName) {
        if (!roomToWindowsMap.containsKey(roomName)) {
            return ResponseEntity.badRequest().body("Room does not exist");
        }
        roomToWindowsMap.remove(roomName);
        return ResponseEntity.ok("Room deleted successfully");
    }

    // Supprimer une fenêtre des associations
    @DeleteMapping("/windows/{windowName}")
    public ResponseEntity<String> deleteWindow(@PathVariable String windowName) {
        roomToWindowsMap.forEach((room, windows) -> windows.remove(windowName));
        return ResponseEntity.ok("Window removed from associations successfully");
    }

    // Mettre à jour un nom de chambre dans les associations
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

    // Mettre à jour un nom de fenêtre dans les associations
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
}

