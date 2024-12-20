package com.example.sensorservice.scheduler;

import com.example.sensorservice.model.Sensor;
import com.example.sensorservice.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class SensorScheduler {

    @Autowired
    private SensorRepository sensorRepository;

    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void simulateSensorData() {
        List<Sensor> sensors = sensorRepository.findAll();
        Random random = new Random();

        sensors.forEach(sensor -> {
            double randomValue = 18 + (27 - 18) * random.nextDouble(); // Random value between 18 and 27
            sensor.setValue(randomValue);
            sensorRepository.save(sensor);  // Update the sensor with the new value
            System.out.println("Sensor ID: " + sensor.getId() + " | Position: " + sensor.getPosition() + " | Value: " + randomValue);
        });
    }
}
