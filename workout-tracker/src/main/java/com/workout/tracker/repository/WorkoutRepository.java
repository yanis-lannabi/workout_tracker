package com.workout.tracker.repository;

import com.workout.tracker.model.User;
import com.workout.tracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUser(User user);
    List<Workout> findByUserAndScheduledDateTimeBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Workout> findByUserAndCompletedDateTimeIsNotNull(User user);
} 