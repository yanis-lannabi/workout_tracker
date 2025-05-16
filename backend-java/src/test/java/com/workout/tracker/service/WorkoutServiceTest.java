package com.workout.tracker.service;

import com.workout.tracker.dto.WorkoutDTO;
import com.workout.tracker.model.User;
import com.workout.tracker.model.Workout;
import com.workout.tracker.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private User testUser;
    private Workout testWorkout;
    private WorkoutDTO testWorkoutDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .build();

        testWorkout = Workout.builder()
                .id(1L)
                .user(testUser)
                .name("Test Workout")
                .scheduledDateTime(LocalDateTime.now())
                .build();

        testWorkoutDTO = WorkoutDTO.builder()
                .id(1L)
                .name("Test Workout")
                .scheduledDateTime(LocalDateTime.now())
                .build();
    }

    @Test
    void getUserWorkouts_ShouldReturnWorkouts() {
        when(workoutRepository.findByUser(testUser)).thenReturn(Arrays.asList(testWorkout));

        List<WorkoutDTO> result = workoutService.getUserWorkouts(testUser);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testWorkout.getName(), result.get(0).getName());
        verify(workoutRepository).findByUser(testUser);
    }

    @Test
    void createWorkout_ShouldCreateNewWorkout() {
        when(workoutRepository.save(any(Workout.class))).thenReturn(testWorkout);

        WorkoutDTO result = workoutService.createWorkout(testUser, testWorkoutDTO);

        assertNotNull(result);
        assertEquals(testWorkoutDTO.getName(), result.getName());
        verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    void updateWorkout_ShouldUpdateExistingWorkout() {
        when(workoutRepository.findById(1L)).thenReturn(Optional.of(testWorkout));
        when(workoutRepository.save(any(Workout.class))).thenReturn(testWorkout);

        WorkoutDTO updatedDTO = testWorkoutDTO;
        updatedDTO.setName("Updated Workout");

        WorkoutDTO result = workoutService.updateWorkout(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated Workout", result.getName());
        verify(workoutRepository).findById(1L);
        verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    void deleteWorkout_ShouldDeleteWorkout() {
        doNothing().when(workoutRepository).deleteById(1L);

        workoutService.deleteWorkout(1L);

        verify(workoutRepository).deleteById(1L);
    }
} 