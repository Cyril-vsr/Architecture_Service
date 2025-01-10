package com.example.sensorservice.repository;

import com.example.sensorservice.model.SensorHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorHistoryRepository extends JpaRepository<SensorHistory, Long> {
    // Méthodes supplémentaires si nécessaires
}
