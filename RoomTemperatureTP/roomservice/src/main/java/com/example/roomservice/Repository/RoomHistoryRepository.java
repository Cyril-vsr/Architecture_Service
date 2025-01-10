package com.example.roomservice.Repository;

import com.example.roomservice.model.RoomHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomHistoryRepository extends JpaRepository<RoomHistory, Long> {
    // Méthodes personnalisées si nécessaires
}
