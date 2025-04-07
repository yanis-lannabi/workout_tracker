package com.workout.tracker.repository;

import com.workout.tracker.model.Exercise;
import com.workout.tracker.model.ExerciseCategory;
import com.workout.tracker.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCategory(ExerciseCategory category);
    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);
    List<Exercise> findByCategoryAndMuscleGroup(ExerciseCategory category, MuscleGroup muscleGroup);
} 