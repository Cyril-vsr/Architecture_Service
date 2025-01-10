package com.example.windowservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "window")
public class Window {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roomId;
    private String name;

    @Column(nullable = false)
    private String windowState = "closed"; // Default value

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getWindowState() { return windowState; }
    public void setWindowState(String windowState) { this.windowState = windowState; }
    
    public Long getRoomId() { return roomId; }

    public void setRoomId(Long roomId) { this.roomId = roomId; }

}


