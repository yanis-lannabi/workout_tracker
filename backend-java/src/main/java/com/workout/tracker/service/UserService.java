package com.workout.tracker.service;

import com.workout.tracker.model.User;
import com.workout.tracker.model.Role;
import com.workout.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User getDefaultUser() {
        return userRepository.findByEmail("default@example.com")
                .orElseGet(() -> {
                    User defaultUser = new User();
                    defaultUser.setEmail("default@example.com");
                    defaultUser.setPassword("default"); // Note: In a real app, this should be properly hashed
                    defaultUser.setRole(Role.USER);
                    return userRepository.save(defaultUser);
                });
    }
} 