package com.example.request_service.repository;

import com.example.request_service.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire, comme trouver par statut
}

