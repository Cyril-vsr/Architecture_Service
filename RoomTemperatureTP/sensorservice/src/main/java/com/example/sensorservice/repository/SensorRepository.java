package com.example.sensorservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sensorservice.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findByRoomId(Long roomId);
}
