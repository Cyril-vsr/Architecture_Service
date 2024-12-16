package com.example.feedback_service.Repository;

import com.example.feedback_service.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Méthodes de recherche personnalisées peuvent être ajoutées ici
}

