package com.example.windowservice.Repository;

import com.example.windowservice.model.WindowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindowHistoryRepository extends JpaRepository<WindowHistory, Long> {
}
