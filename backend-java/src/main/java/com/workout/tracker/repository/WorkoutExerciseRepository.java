package com.workout.tracker.repository;

import com.workout.tracker.model.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
    List<WorkoutExercise> findByWorkoutId(Long workoutId);
} 