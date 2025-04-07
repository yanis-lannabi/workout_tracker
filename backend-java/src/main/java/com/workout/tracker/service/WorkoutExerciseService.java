package com.workout.tracker.service;

import com.workout.tracker.dto.ExerciseDTO;
import com.workout.tracker.dto.WorkoutExerciseDTO;
import com.workout.tracker.model.Exercise;
import com.workout.tracker.model.Workout;
import com.workout.tracker.model.WorkoutExercise;
import com.workout.tracker.repository.ExerciseRepository;
import com.workout.tracker.repository.WorkoutExerciseRepository;
import com.workout.tracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public List<WorkoutExerciseDTO> getWorkoutExercises(Long workoutId) {
        return workoutExerciseRepository.findByWorkoutId(workoutId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkoutExerciseDTO addExerciseToWorkout(Long workoutId, WorkoutExerciseDTO workoutExerciseDTO) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        Exercise exercise = exerciseRepository.findById(workoutExerciseDTO.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkout(workout);
        workoutExercise.setExercise(exercise);
        workoutExercise.setSets(workoutExerciseDTO.getSets());
        workoutExercise.setRepetitions(workoutExerciseDTO.getRepetitions());
        workoutExercise.setWeight(workoutExerciseDTO.getWeight());
        workoutExercise.setNotes(workoutExerciseDTO.getNotes());

        WorkoutExercise savedExercise = workoutExerciseRepository.save(workoutExercise);
        return convertToDTO(savedExercise);
    }

    @Transactional
    public WorkoutExerciseDTO updateWorkoutExercise(Long id, WorkoutExerciseDTO workoutExerciseDTO) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout exercise not found"));

        workoutExercise.setSets(workoutExerciseDTO.getSets());
        workoutExercise.setRepetitions(workoutExerciseDTO.getRepetitions());
        workoutExercise.setWeight(workoutExerciseDTO.getWeight());
        workoutExercise.setNotes(workoutExerciseDTO.getNotes());

        WorkoutExercise updatedExercise = workoutExerciseRepository.save(workoutExercise);
        return convertToDTO(updatedExercise);
    }

    @Transactional
    public void deleteWorkoutExercise(Long id) {
        workoutExerciseRepository.deleteById(id);
    }

    private WorkoutExerciseDTO convertToDTO(WorkoutExercise workoutExercise) {
        return WorkoutExerciseDTO.builder()
                .id(workoutExercise.getId())
                .exerciseId(workoutExercise.getExercise().getId())
                .sets(workoutExercise.getSets())
                .repetitions(workoutExercise.getRepetitions())
                .weight(workoutExercise.getWeight())
                .notes(workoutExercise.getNotes())
                .build();
    }

    private ExerciseDTO convertToExerciseDTO(Exercise exercise) {
        return ExerciseDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .category(exercise.getCategory())
                .muscleGroup(exercise.getMuscleGroup())
                .build();
    }
} 