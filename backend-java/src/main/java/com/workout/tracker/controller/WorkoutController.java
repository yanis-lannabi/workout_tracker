package com.workout.tracker.controller;

import com.workout.tracker.dto.WorkoutDTO;
import com.workout.tracker.model.User;
import com.workout.tracker.service.UserService;
import com.workout.tracker.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getUserWorkouts(@AuthenticationPrincipal User user) {
        User currentUser = user != null ? user : userService.getDefaultUser();
        List<WorkoutDTO> workouts = workoutService.getUserWorkouts(currentUser);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(workouts);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<WorkoutDTO>> getUserCompletedWorkouts(@AuthenticationPrincipal User user) {
        User currentUser = user != null ? user : userService.getDefaultUser();
        List<WorkoutDTO> workouts = workoutService.getUserCompletedWorkouts(currentUser);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(workouts);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<WorkoutDTO>> getUserWorkoutsBetweenDates(
            @AuthenticationPrincipal User user,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        User currentUser = user != null ? user : userService.getDefaultUser();
        List<WorkoutDTO> workouts = workoutService.getUserWorkoutsBetweenDates(currentUser, start, end);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(workouts);
    }

    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(
            @AuthenticationPrincipal User user,
            @RequestBody WorkoutDTO workoutDTO) {
        User currentUser = user != null ? user : userService.getDefaultUser();
        WorkoutDTO createdWorkout = workoutService.createWorkout(currentUser, workoutDTO);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdWorkout);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> updateWorkout(
            @PathVariable Long id,
            @RequestBody WorkoutDTO workoutDTO) {
        WorkoutDTO updatedWorkout = workoutService.updateWorkout(id, workoutDTO);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedWorkout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.ok().build();
    }
} 