package com.example.windowservice.controller;

import com.example.windowservice.model.Window;
import com.example.windowservice.model.WindowHistory;
import com.example.windowservice.service.WindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/windows")
public class WindowController {

    @Autowired
    private WindowService windowService;

    @GetMapping
    public List<Window> getAllWindows() {
        return windowService.getAllWindows();
    }

    @GetMapping("/{id}")
    public Optional<Window> getWindowById(@PathVariable Long id) {
        return windowService.getWindowById(id);
    }

    @PostMapping
    public Window addWindow(@RequestBody Window window) {
        return windowService.addWindow(window);
    }

    @DeleteMapping("/{id}")
    public void removeWindow(@PathVariable Long id) {
        windowService.removeWindow(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Window> updateWindow(@PathVariable Long id, @RequestBody Window windowDetails) {
        Window updatedWindow = windowService.updateWindow(id, windowDetails);
        return ResponseEntity.ok(updatedWindow);
    }


    @GetMapping("/history")
    public List<WindowHistory> getHistoricWindow() {
        return windowService.getHistoricWindow();
    }   

    @DeleteMapping("/history")
    public ResponseEntity<String> deleteHistoricWindow() {
        windowService.deleteHistoricWindow();
        return ResponseEntity.ok("All history deleted successfully.");
    }

    // New endpoint to get windows by room ID
    @GetMapping("/room/{roomId}")
    public scala.collection.immutable.List<Window> getWindowsByRoomId(@PathVariable Long roomId) {
        return windowService.getWindowsByRoomId(roomId);
    }
}