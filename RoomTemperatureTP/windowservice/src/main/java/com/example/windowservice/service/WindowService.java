package com.example.windowservice.service;

import com.example.windowservice.model.Window;
import com.example.windowservice.model.WindowHistory;
import com.example.windowservice.Repository.WindowRepository;
import com.example.windowservice.Repository.WindowHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WindowService {

    @Autowired
    private WindowRepository windowRepository;

    @Autowired
    private WindowHistoryRepository windowHistoryRepository;

    public List<Window> getAllWindows() {
        return windowRepository.findAll();
    }

    public Window addWindow(Window window) {
        Window newWindow = windowRepository.save(window);
    
        // Enregistrement dans l'historique
        WindowHistory history = new WindowHistory();
        history.setOperation("add");
        history.setDetails("Added window with name: " + newWindow.getName());
        history.setTimestamp(java.time.LocalDateTime.now());
        windowHistoryRepository.save(history);
    
        return newWindow;
    }

    public Optional<Window> getWindowById(Long id) {
        return windowRepository.findById(id);
    }

    public void removeWindow(Long id) {
        Optional<Window> window = windowRepository.findById(id);

        if (window.isPresent()) {
            windowRepository.deleteById(id);

            // Enregistrer l'historique
            recordHistory("delete", "Window deleted: " + window.get().getName());
        }
    }

    public Window updateWindow(Long id, Window windowDetails) {
        Window window = windowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Window not found"));

        if (windowDetails.getName() != null) {
            window.setName(windowDetails.getName());
        }

        if (windowDetails.getWindowState() != null) {
            window.setWindowState(windowDetails.getWindowState());
        }

        // Sauvegarde de la fenêtre mise à jour
        Window updatedWindow = windowRepository.save(window);

        // Enregistrement de l'historique
        WindowHistory history = new WindowHistory();
        history.setOperation("update");
        history.setDetails("Updated window with name: " + windowDetails.getName() + "state is now: " + windowDetails.getWindowState());
        history.setTimestamp(java.time.LocalDateTime.now());
        windowHistoryRepository.save(history);

        return updatedWindow;
    }

    public List<WindowHistory> getHistoricWindow() {
        return windowHistoryRepository.findAll();
    }

    private void recordHistory(String operation, String details) {
        WindowHistory history = new WindowHistory();
        history.setOperation(operation);
        history.setDetails(details);
        history.setTimestamp(LocalDateTime.now());
        windowHistoryRepository.save(history);
    }

    public void deleteHistoricWindow() {
        windowHistoryRepository.deleteAll();
    }
}
