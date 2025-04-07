package com.workout.tracker.controller;

import com.workout.tracker.dto.WorkoutExerciseDTO;
import com.workout.tracker.service.WorkoutExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts/{workoutId}/exercises")
@RequiredArgsConstructor
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;

    @GetMapping
    public ResponseEntity<List<WorkoutExerciseDTO>> getWorkoutExercises(@PathVariable Long workoutId) {
        return ResponseEntity.ok(workoutExerciseService.getWorkoutExercises(workoutId));
    }

    @PostMapping
    public ResponseEntity<WorkoutExerciseDTO> addExerciseToWorkout(
            @PathVariable Long workoutId,
            @RequestBody WorkoutExerciseDTO workoutExerciseDTO) {
        return ResponseEntity.ok(workoutExerciseService.addExerciseToWorkout(workoutId, workoutExerciseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutExerciseDTO> updateWorkoutExercise(
            @PathVariable Long id,
            @RequestBody WorkoutExerciseDTO workoutExerciseDTO) {
        return ResponseEntity.ok(workoutExerciseService.updateWorkoutExercise(id, workoutExerciseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutExercise(@PathVariable Long id) {
        workoutExerciseService.deleteWorkoutExercise(id);
        return ResponseEntity.ok().build();
    }
} 