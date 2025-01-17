package com.example.windowservice.Repository;

import com.example.windowservice.model.Window;

import scala.collection.immutable.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WindowRepository extends JpaRepository<Window, Long> {
    // Méthodes de recherche personnalisées peuvent être ajoutées ici
    List<Window> findByRoomId(Long roomId);
}
