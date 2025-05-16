package com.workout.tracker.config;

import com.workout.tracker.model.User;
import com.workout.tracker.model.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if ("test@example.com".equals(username)) {
                    // Créer notre modèle User personnalisé
                    User user = User.builder()
                            .id(1L)
                            .email("test@example.com")
                            .firstName("Test")
                            .lastName("User")
                            .role(Role.USER)
                            .build();

                    // Créer l'authentification avec notre User personnalisé
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            user,
                            passwordEncoder().encode("password"),
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                    
                    // Définir l'authentification dans le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Retourner un UserDetails standard pour la configuration de sécurité
                    return org.springframework.security.core.userdetails.User
                            .withUsername("test@example.com")
                            .password(passwordEncoder().encode("password"))
                            .authorities("ROLE_USER")
                            .build();
                }
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 