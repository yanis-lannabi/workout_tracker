package com.workout.tracker.dto;

import com.workout.tracker.model.ExerciseCategory;
import com.workout.tracker.model.MuscleGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private Long id;
    private String name;
    private String description;
    private ExerciseCategory category;
    private MuscleGroup muscleGroup;
} 