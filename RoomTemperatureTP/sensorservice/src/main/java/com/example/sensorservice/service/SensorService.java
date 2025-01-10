package com.example.sensorservice.service;

import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.model.SensorHistory;
import com.example.sensorservice.repository.SensorRepository;
import com.example.sensorservice.repository.SensorHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorHistoryRepository sensorHistoryRepository;

    // Add Sensor
    public Sensor addSensor(Sensor sensor) {
        Sensor newSensor = sensorRepository.save(sensor);

        // Enregistrer l'opération dans l'historique
        saveHistory("add", "Added sensor with ID: " + newSensor.getId() + ", Name: " + newSensor.getName()+" position: " + newSensor.getPosition());

        return newSensor;
    }

    // Delete Sensor
    public void deleteSensor(int sensorId) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        sensor.ifPresent(s -> {
            sensorRepository.deleteById(sensorId);
            saveHistory("delete", "Deleted sensor with ID: " + s.getId() + ", Name: " + s.getName()+ " position: " + s.getPosition());
        });
    }

    // Send Data (Update the value of the sensor)
    public Sensor sendData(int sensorId, double value) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        if (sensor.isPresent()) {
            Sensor s = sensor.get();
            s.setValue(value);
            saveHistory("update", "Updated sensor value for ID: " + s.getId() + ", New Value: " + value +"position: " + s.getPosition());
            return sensorRepository.save(s);
        }
        return null;
    }

    // Set Position
    public Sensor setPosition(int sensorId, String position) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        if (sensor.isPresent()) {
            Sensor s = sensor.get();
            s.setPosition(position);
            saveHistory("update", "Updated sensor position for ID: " + s.getId() + ", New Position: " + position);
            return sensorRepository.save(s);
        }
        return null;
    }

    // Get Sensor by ID
    public Optional<Sensor> getSensorById(int sensorId) {
        return sensorRepository.findById(sensorId);
    }

    // Get All Sensors
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    // Get Position
    public Optional<String> getPosition(int sensorId) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);
        return sensor.map(Sensor::getPosition);
    }

    // Enregistrer une opération dans l'historique
    private void saveHistory(String operation, String details) {
        SensorHistory history = new SensorHistory();
        history.setOperation(operation);
        history.setDetails(details);
        history.setTimestamp(LocalDateTime.now());
        sensorHistoryRepository.save(history);
    }

    // Récupérer tout l'historique
    public List<SensorHistory> getHistoricSensor() {
        return sensorHistoryRepository.findAll();
    }

    // Supprimer tout l'historique
    public void deleteHistoricSensor() {
        sensorHistoryRepository.deleteAll();
    }

    // New method to get sensors by room ID
    public List<Sensor> getSensorsByRoomId(Long roomId) {
        return sensorRepository.findByRoomId(roomId);
    }
}
