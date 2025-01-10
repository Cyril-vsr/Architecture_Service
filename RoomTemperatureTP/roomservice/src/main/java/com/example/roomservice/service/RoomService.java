package com.example.roomservice.service;

import com.example.roomservice.model.Room;
import com.example.roomservice.model.RoomHistory;
import com.example.roomservice.Repository.RoomHistoryRepository;
import com.example.roomservice.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomHistoryRepository roomHistoryRepository;


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room addRoom(Room room) {
        Room newRoom = roomRepository.save(room);

        // Enregistrer l'opération dans l'historique
        RoomHistory history = new RoomHistory();
        history.setOperation("add");
        history.setDetails("Added room with name: " + newRoom.getId() + ", Name: " + newRoom.getName());
        history.setTimestamp(LocalDateTime.now());
        roomHistoryRepository.save(history);

        return newRoom;
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (roomDetails.getName() != null) {
            room.setName(roomDetails.getName());
        }

        Room updatedRoom = roomRepository.save(room);

        // Enregistrer l'opération dans l'historique
        RoomHistory history = new RoomHistory();
        history.setOperation("update");
        history.setDetails("Updated room with ID: " + updatedRoom.getId() + ", Name: " + updatedRoom.getName());
        history.setTimestamp(LocalDateTime.now());
        roomHistoryRepository.save(history);

        return updatedRoom;
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public void removeRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomRepository.deleteById(id);

        // Enregistrer l'opération dans l'historique
        RoomHistory history = new RoomHistory();
        history.setOperation("delete");
        history.setDetails("Deleted room with ID: " + id + ", Name: " + room.getName());
        history.setTimestamp(LocalDateTime.now());
        roomHistoryRepository.save(history);
    }

    public List<RoomHistory> getHistoricRoom() {
        return roomHistoryRepository.findAll();
    }

    public void deleteHistoricRoom() {
        roomHistoryRepository.deleteAll();
    }
}
