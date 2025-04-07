package com.workout.tracker.controller;

import com.workout.tracker.dto.ExerciseDTO;
import com.workout.tracker.model.ExerciseCategory;
import com.workout.tracker.model.MuscleGroup;
import com.workout.tracker.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByCategory(@PathVariable ExerciseCategory category) {
        return ResponseEntity.ok(exerciseService.getExercisesByCategory(category));
    }

    @GetMapping("/muscle-group/{muscleGroup}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByMuscleGroup(@PathVariable MuscleGroup muscleGroup) {
        return ResponseEntity.ok(exerciseService.getExercisesByMuscleGroup(muscleGroup));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }
} 