package com.workout.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseDTO {
    private Long id;
    private Long exerciseId;
    private Integer sets;
    private Integer repetitions;
    private Double weight;
    private String notes;
} 