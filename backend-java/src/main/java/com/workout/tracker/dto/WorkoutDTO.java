package com.workout.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime completedDateTime;
    private String comments;
    private String status;
    private List<WorkoutExerciseDTO> exercises;
} 