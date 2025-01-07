package com.example.roomservice.Repository;

import com.example.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // Méthodes de recherche personnalisées peuvent être ajoutées ici
}
