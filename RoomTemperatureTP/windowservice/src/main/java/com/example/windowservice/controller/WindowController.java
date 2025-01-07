package com.example.windowservice.controller;

import com.example.windowservice.model.Window;
import com.example.windowservice.service.WindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Window window = windowService.getWindowById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Window not found"));

        if (windowDetails.getName() != null) {
            window.setName(windowDetails.getName());
        }

        if (windowDetails.getWindowState() != null) {
            window.setWindowState(windowDetails.getWindowState());
        }

        Window updatedWindow = windowService.addWindow(window);
        return ResponseEntity.ok(updatedWindow);
    }

}