package com.example.sensorservice.service;

import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    // Add Sensor
    public Sensor addSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    // Delete Sensor
    public void deleteSensor(int sensorId) {
        sensorRepository.deleteById(sensorId);
    }

    // Send Data (Update the value of the sensor)
    public Sensor sendData(int sensorId, double value) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        if (sensor.isPresent()) {
            sensor.get().setValue(value);
            return sensorRepository.save(sensor.get());
        }
        return null;
    }

    // Get Sensor by ID
    public Optional<Sensor> getSensorById(int sensorId) {
        return sensorRepository.findById(sensorId);
    }

    // Méthode pour récupérer tous les capteurs
       public List<Sensor> getAllSensors() {
        return sensorRepository.findAll(); // Utilise la méthode findAll() de Spring Data JPA
    }


    // Set Position
    public Sensor setPosition(int sensorId, String position) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        if (sensor.isPresent()) {
            sensor.get().setPosition(position);
            return sensorRepository.save(sensor.get());
        }
        return null;
    }

    // Get Position
    public Optional<String> getPosition(int sensorId) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        return sensor.map(Sensor::getPosition);
    }

    // New method to get sensors by room ID
    public List<Sensor> getSensorsByRoomId(Long roomId) {
        return sensorRepository.findByRoomId(roomId);
    }
}
