package com.workout.tracker.controller;

import com.workout.tracker.dto.WorkoutDTO;
import com.workout.tracker.model.User;
import com.workout.tracker.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getUserWorkouts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(workoutService.getUserWorkouts(user));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<WorkoutDTO>> getUserCompletedWorkouts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(workoutService.getUserCompletedWorkouts(user));
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<WorkoutDTO>> getUserWorkoutsBetweenDates(
            @AuthenticationPrincipal User user,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(workoutService.getUserWorkoutsBetweenDates(user, start, end));
    }

    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(
            @AuthenticationPrincipal User user,
            @RequestBody WorkoutDTO workoutDTO) {
        return ResponseEntity.ok(workoutService.createWorkout(user, workoutDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> updateWorkout(
            @PathVariable Long id,
            @RequestBody WorkoutDTO workoutDTO) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workoutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.ok().build();
    }
} 