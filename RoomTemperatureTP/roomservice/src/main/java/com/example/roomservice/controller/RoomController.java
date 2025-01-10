package com.example.roomservice.controller;

import com.example.roomservice.model.Room;
import com.example.roomservice.model.RoomHistory;
import com.example.roomservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Optional<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @DeleteMapping("/{id}")
    public void removeRoom(@PathVariable Long id) {
        roomService.removeRoom(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Room updatedRoom = roomService.updateRoom(id, roomDetails);
        return ResponseEntity.ok(updatedRoom);
    }

    @GetMapping("/history")
    public List<RoomHistory> getHistoricRoom() {
        return roomService.getHistoricRoom();
    }

    @DeleteMapping("/history")
    public ResponseEntity<String> deleteHistoricRoom() {
        roomService.deleteHistoricRoom();
        return ResponseEntity.ok("All history deleted successfully.");
    }

}