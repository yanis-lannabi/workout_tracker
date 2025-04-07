package com.workout.tracker.service;

import com.workout.tracker.model.User;
import com.workout.tracker.model.Workout;
import com.workout.tracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final WorkoutRepository workoutRepository;

    public Map<String, Object> generateUserProgressReport(User user, OffsetDateTime startDate, OffsetDateTime endDate) {
        LocalDateTime startLocalDateTime = startDate.toLocalDateTime();
        LocalDateTime endLocalDateTime = endDate.toLocalDateTime();

        List<Workout> workouts = workoutRepository.findByUserAndScheduledDateTimeBetween(user, startLocalDateTime, endLocalDateTime);

        Map<String, Object> report = new HashMap<>();
        report.put("totalWorkouts", workouts.size());
        report.put("completedWorkouts", workouts.stream()
                .filter(workout -> workout.getCompletedDateTime() != null)
                .count());
        report.put("averageWorkoutsPerWeek", calculateAverageWorkoutsPerWeek(workouts));
        report.put("workoutDistribution", calculateWorkoutDistribution(workouts));

        return report;
    }

    private double calculateAverageWorkoutsPerWeek(List<Workout> workouts) {
        if (workouts.isEmpty()) {
            return 0.0;
        }

        LocalDateTime firstWorkout = workouts.stream()
                .map(Workout::getScheduledDateTime)
                .min(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        LocalDateTime lastWorkout = workouts.stream()
                .map(Workout::getScheduledDateTime)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        long weeks = java.time.temporal.ChronoUnit.WEEKS.between(firstWorkout, lastWorkout) + 1;
        return (double) workouts.size() / weeks;
    }

    private Map<String, Long> calculateWorkoutDistribution(List<Workout> workouts) {
        Map<String, Long> distribution = new HashMap<>();
        workouts.forEach(workout -> {
            String dayOfWeek = workout.getScheduledDateTime().getDayOfWeek().name();
            distribution.merge(dayOfWeek, 1L, Long::sum);
        });
        return distribution;
    }
} 