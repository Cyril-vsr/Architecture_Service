package com.example.sensorservice.controller;

import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // Add a new sensor
    @PostMapping("/add")
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor) {
        return ResponseEntity.ok(sensorService.addSensor(sensor));
    }

    // Delete a sensor by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable int id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.ok().build();
    }

    // Send data to an existing sensor
    @PostMapping("/send-data/{id}")
    public ResponseEntity<Sensor> sendData(@PathVariable int id, @RequestParam double value) {
        Sensor updatedSensor = sensorService.sendData(id, value);
        if (updatedSensor != null) {
            return ResponseEntity.ok(updatedSensor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get sensor data by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Sensor> getSensor(@PathVariable int id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        return sensor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sensor>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        return ResponseEntity.ok(sensors);
    }

    // Set sensor position
    @PostMapping("/set-position/{id}")
    public ResponseEntity<Sensor> setPosition(@PathVariable int id, @RequestParam String position) {
        Sensor updatedSensor = sensorService.setPosition(id, position);
        return ResponseEntity.ok(updatedSensor);
    }

    // Get sensor position by ID
    @GetMapping("/get-position/{id}")
    public ResponseEntity<String> getPosition(@PathVariable int id) {
        Optional<String> position = sensorService.getPosition(id);
        return position.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // New endpoint to get sensors by room ID
    @GetMapping("/room/{roomId}")
    public List<Sensor> getSensorsByRoomId(@PathVariable Long roomId) {
        return sensorService.getSensorsByRoomId(roomId);
    }
}
