package com.workout.tracker.service;

import com.workout.tracker.model.Exercise;
import com.workout.tracker.model.ExerciseCategory;
import com.workout.tracker.model.MuscleGroup;
import com.workout.tracker.model.Workout;
import com.workout.tracker.repository.ExerciseRepository;
import com.workout.tracker.repository.WorkoutRepository;
import com.workout.tracker.model.User;
import com.workout.tracker.model.WorkoutExercise;
import com.workout.tracker.repository.UserRepository;
import com.workout.tracker.repository.WorkoutExerciseRepository;
import com.workout.tracker.model.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitializationService {
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    @PostConstruct
    public void initializeData() {
        initializeUsers();
        initializeExercises();
        initializeWorkouts();
        initializeWorkoutExercises();
    }

    public void initializeExercises() {
        if (exerciseRepository.count() == 0) {
            List<Exercise> exercises = Arrays.asList(
                    createExercise("Push-ups", "A classic bodyweight exercise for the chest and arms", ExerciseCategory.STRENGTH, MuscleGroup.CHEST),
                    createExercise("Pull-ups", "An upper body exercise that targets the back and arms", ExerciseCategory.STRENGTH, MuscleGroup.BACK),
                    createExercise("Squats", "A lower body exercise that targets the legs and glutes", ExerciseCategory.STRENGTH, MuscleGroup.LEGS),
                    createExercise("Plank", "A core exercise that improves stability and endurance", ExerciseCategory.STRENGTH, MuscleGroup.ABS),
                    createExercise("Running", "A cardiovascular exercise that improves endurance", ExerciseCategory.CARDIO, MuscleGroup.FULL_BODY),
                    createExercise("Yoga", "A flexibility and balance exercise that improves mobility", ExerciseCategory.FLEXIBILITY, MuscleGroup.FULL_BODY),
                    createExercise("Deadlift", "A compound exercise that targets multiple muscle groups", ExerciseCategory.STRENGTH, MuscleGroup.FULL_BODY),
                    createExercise("Bench Press", "A strength exercise that targets the chest and arms", ExerciseCategory.STRENGTH, MuscleGroup.CHEST),
                    createExercise("Shoulder Press", "An upper body exercise that targets the shoulders", ExerciseCategory.STRENGTH, MuscleGroup.SHOULDERS),
                    createExercise("Bicep Curls", "An arm exercise that targets the biceps", ExerciseCategory.STRENGTH, MuscleGroup.ARMS)
            );

            exerciseRepository.saveAll(exercises);
        }
    }

    private Exercise createExercise(String name, String description, ExerciseCategory category, MuscleGroup muscleGroup) {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setCategory(category);
        exercise.setMuscleGroup(muscleGroup);
        return exercise;
    }

    private void initializeWorkouts() {
        if (workoutRepository.count() == 0) {
            // Fetch a user to associate with the workouts
            User user = userRepository.findAll().stream().findFirst().orElse(null);
            if (user != null) {
                List<Workout> workouts = Arrays.asList(
                        createWorkout("Morning Run", "A refreshing morning run.", LocalDateTime.now().minusDays(1), user),
                        createWorkout("Evening Yoga", "Relaxing yoga session.", LocalDateTime.now().minusDays(2), user),
                        createWorkout("Strength Training", "Full body strength workout.", LocalDateTime.now().minusDays(3), user)
                );

                workoutRepository.saveAll(workouts);
            } else {
                System.out.println("No users found to associate with workouts.");
            }
        }
    }

    private Workout createWorkout(String name, String description, LocalDateTime dateTime, User user) {
        Workout workout = new Workout();
        workout.setName(name);
        workout.setDescription(description);
        workout.setScheduledDateTime(dateTime);
        workout.setUser(user); // Set the user for the workout
        return workout;
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                    createUser("john.doe@example.com", "password", "John", "Doe", Role.USER),
                    createUser("jane.smith@example.com", "password", "Jane", "Smith", Role.USER)
            );

            userRepository.saveAll(users);
        }
    }

    private User createUser(String email, String password, String firstName, String lastName, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        return user;
    }

    private void initializeWorkoutExercises() {
        if (workoutExerciseRepository.count() == 0) {
            // Fetch a workout to associate with the workout exercises
            Workout workout = workoutRepository.findAll().stream().findFirst().orElse(null);
            if (workout != null) {
                List<WorkoutExercise> workoutExercises = Arrays.asList(
                        createWorkoutExercise(workout.getId(), 1L, 3, 10, 50.0, "Warm-up set"),
                        createWorkoutExercise(workout.getId(), 2L, 4, 8, 70.0, "Main set")
                );

                workoutExerciseRepository.saveAll(workoutExercises);
            } else {
                System.out.println("No workouts found to associate with workout exercises.");
            }
        }
    }

    private WorkoutExercise createWorkoutExercise(Long workoutId, Long exerciseId, Integer sets, Integer repetitions, Double weight, String notes) {
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkout(workoutRepository.findById(workoutId).orElse(null));
        workoutExercise.setExercise(exerciseRepository.findById(exerciseId).orElse(null));
        workoutExercise.setSets(sets);
        workoutExercise.setRepetitions(repetitions);
        workoutExercise.setWeight(weight);
        workoutExercise.setNotes(notes);
        return workoutExercise;
    }
} 