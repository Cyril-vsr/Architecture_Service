package com.example.sensorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sensorservice.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
