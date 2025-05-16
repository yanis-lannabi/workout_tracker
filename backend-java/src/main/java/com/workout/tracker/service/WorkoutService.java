package com.workout.tracker.service;

import com.workout.tracker.dto.WorkoutDTO;
import com.workout.tracker.model.User;
import com.workout.tracker.model.Workout;
import com.workout.tracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseService workoutExerciseService;

    public List<WorkoutDTO> getUserWorkouts(User user) {
        return workoutRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<WorkoutDTO> getUserWorkoutsBetweenDates(User user, LocalDateTime start, LocalDateTime end) {
        return workoutRepository.findByUserAndScheduledDateTimeBetween(user, start, end).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<WorkoutDTO> getUserCompletedWorkouts(User user) {
        return workoutRepository.findByUserAndCompletedDateTimeIsNotNull(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WorkoutDTO createWorkout(User user, WorkoutDTO workoutDTO) {
        Workout workout = new Workout();
        workout.setUser(user);
        workout.setName(workoutDTO.getName());
        workout.setDescription(workoutDTO.getDescription());
        workout.setScheduledDateTime(workoutDTO.getScheduledDateTime());
        workout.setComments(workoutDTO.getComments());

        Workout savedWorkout = workoutRepository.save(workout);
        return convertToDTO(savedWorkout);
    }

    public WorkoutDTO updateWorkout(Long id, WorkoutDTO workoutDTO) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        workout.setName(workoutDTO.getName());
        workout.setDescription(workoutDTO.getDescription());
        workout.setScheduledDateTime(workoutDTO.getScheduledDateTime());
        workout.setCompletedDateTime(workoutDTO.getCompletedDateTime());
        workout.setComments(workoutDTO.getComments());

        Workout updatedWorkout = workoutRepository.save(workout);
        return convertToDTO(updatedWorkout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    private WorkoutDTO convertToDTO(Workout workout) {
        WorkoutDTO dto = new WorkoutDTO();
        dto.setId(workout.getId());
        dto.setName(workout.getName());
        dto.setDescription(workout.getDescription());
        dto.setScheduledDateTime(workout.getScheduledDateTime());
        dto.setCompletedDateTime(workout.getCompletedDateTime());
        dto.setComments(workout.getComments());
        dto.setExercises(workoutExerciseService.getWorkoutExercises(workout.getId()));
        return dto;
    }
} 