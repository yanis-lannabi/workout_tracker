package com.workout.tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workout.tracker.config.TestSecurityConfig;
import com.workout.tracker.dto.WorkoutDTO;
import com.workout.tracker.model.User;
import com.workout.tracker.model.Role;
import com.workout.tracker.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WorkoutController.class)
@Import(TestSecurityConfig.class)
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkoutService workoutService;

    private User testUser;
    private WorkoutDTO testWorkoutDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .role(Role.USER)
                .build();

        testWorkoutDTO = WorkoutDTO.builder()
                .id(1L)
                .name("Test Workout")
                .description("Test Description")
                .scheduledDateTime(LocalDateTime.now())
                .build();
    }

    @Test
    void getUserWorkouts_ShouldReturnWorkouts() throws Exception {
        // Given
        List<WorkoutDTO> workouts = Arrays.asList(testWorkoutDTO);
        when(workoutService.getUserWorkouts(any(User.class))).thenReturn(workouts);

        // When & Then
        mockMvc.perform(get("/api/workouts")
                .with(csrf())
                .with(user(testUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Workout"))
                .andExpect(jsonPath("$[0].description").value("Test Description"));

        verify(workoutService).getUserWorkouts(any(User.class));
    }

    @Test
    void createWorkout_ShouldCreateNewWorkout() throws Exception {
        // Given
        WorkoutDTO newWorkoutDTO = WorkoutDTO.builder()
                .id(2L)
                .name("New Workout")
                .description("New Description")
                .scheduledDateTime(LocalDateTime.now())
                .build();

        when(workoutService.createWorkout(any(User.class), eq(newWorkoutDTO)))
                .thenReturn(newWorkoutDTO);

        // When & Then
        mockMvc.perform(post("/api/workouts")
                .with(csrf())
                .with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newWorkoutDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("New Workout"))
                .andExpect(jsonPath("$.description").value("New Description"));

        verify(workoutService).createWorkout(any(User.class), eq(newWorkoutDTO));
    }

    @Test
    void updateWorkout_ShouldUpdateWorkout() throws Exception {
        // Given
        WorkoutDTO updatedWorkoutDTO = WorkoutDTO.builder()
                .id(1L)
                .name("Updated Workout")
                .description("Updated Description")
                .scheduledDateTime(LocalDateTime.now())
                .build();

        when(workoutService.updateWorkout(eq(1L), eq(updatedWorkoutDTO)))
                .thenReturn(updatedWorkoutDTO);

        // When & Then
        mockMvc.perform(put("/api/workouts/1")
                .with(csrf())
                .with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedWorkoutDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Workout"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(workoutService).updateWorkout(eq(1L), eq(updatedWorkoutDTO));
    }

    @Test
    void deleteWorkout_ShouldDeleteWorkout() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/workouts/1")
                .with(csrf())
                .with(user(testUser)))
                .andExpect(status().isOk());

        verify(workoutService).deleteWorkout(eq(1L));
    }
} 