package com.example.gateway.model;

public class Sensor {
    private int id;
    private String name;
    private String type;
    private String position;
    private double value;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
