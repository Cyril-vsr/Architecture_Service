package com.example.windowservice.service;

import com.example.windowservice.model.Window;
import com.example.windowservice.Repository.WindowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WindowService {

    @Autowired
    private WindowRepository windowRepository;

    public List<Window> getAllWindows() {
        return windowRepository.findAll();
    }

    public Window addWindow(Window window) {
        return windowRepository.save(window);
    }

    public Optional<Window> getWindowById(Long id) {
        return windowRepository.findById(id);
    }

    public void removeWindow(Long id) {
        windowRepository.deleteById(id);
    }
}
