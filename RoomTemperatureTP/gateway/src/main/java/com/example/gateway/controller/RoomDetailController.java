package com.example.gateway.controller;

import com.example.gateway.service.RoomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms/details")
public class RoomDetailController {

    @Autowired
    private RoomDetailService roomDetailService;

    // Obtenir les détails consolidés
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRoomDetails() {
        return ResponseEntity.ok(roomDetailService.getAllRoomDetails());
    }

    // Ajouter une room
    @PostMapping
    public ResponseEntity<String> addRoom(@RequestBody Map<String, String> roomData) {
        return ResponseEntity.ok(roomDetailService.addRoom(roomData));
    }

    // Ajouter une fenêtre à une room
    @PostMapping("/{roomId}/windows")
    public ResponseEntity<String> addWindowToRoom(@PathVariable Long roomId, @RequestBody Map<String, String> windowData) {
        return ResponseEntity.ok(roomDetailService.addWindowToRoom(roomId, windowData));
    }

    // Ajouter un capteur à une fenêtre
    @PostMapping("/windows/{windowId}/sensors")
    public ResponseEntity<String> addSensorToWindow(@PathVariable Long windowId, @RequestBody Map<String, Object> sensorData) {
        return ResponseEntity.ok(roomDetailService.addSensorToWindow(windowId, sensorData));
    }
}
