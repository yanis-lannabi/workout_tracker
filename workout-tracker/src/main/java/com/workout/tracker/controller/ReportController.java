package com.workout.tracker.controller;

import com.workout.tracker.model.User;
import com.workout.tracker.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/progress")
    public ResponseEntity<Map<String, Object>> getUserProgressReport(
            @AuthenticationPrincipal User user,
            @RequestParam OffsetDateTime startDate,
            @RequestParam OffsetDateTime endDate) {
        return ResponseEntity.ok(reportService.generateUserProgressReport(user, startDate, endDate));
    }
} 