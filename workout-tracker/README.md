# Workout Tracker Application

This is a Spring Boot application for tracking workouts and exercises. It provides a RESTful API for managing users, exercises, and workout sessions.

## Features

- User authentication with JWT
- Exercise management
- Workout tracking
- Progress reports

## Technologies Used

- Spring Boot
- Spring Security
- JWT Authentication
- PostgreSQL
- JPA/Hibernate
- Lombok
- Maven

## Getting Started

### Prerequisites

- Java 17
- Maven
- PostgreSQL

### Installation

1. Clone the repository
2. Configure the database connection in `application.yml`
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Exercises

- `GET /api/exercises` - Get all exercises
- `GET /api/exercises/category/{category}` - Get exercises by category
- `GET /api/exercises/muscle-group/{muscleGroup}` - Get exercises by muscle group
- `GET /api/exercises/{id}` - Get exercise by ID

### Workouts

- `GET /api/workouts` - Get user's workouts
- `GET /api/workouts/completed` - Get user's completed workouts
- `GET /api/workouts/between-dates` - Get user's workouts between dates
- `POST /api/workouts` - Create a new workout
- `PUT /api/workouts/{id}` - Update a workout
- `DELETE /api/workouts/{id}` - Delete a workout

### Workout Exercises

- `GET /api/workouts/{workoutId}/exercises` - Get exercises in a workout
- `POST /api/workouts/{workoutId}/exercises` - Add an exercise to a workout
- `PUT /api/workouts/{workoutId}/exercises/{id}` - Update an exercise in a workout
- `DELETE /api/workouts/{workoutId}/exercises/{id}` - Delete an exercise from a workout

### Reports

- `GET /api/reports/progress` - Get user's progress report

## Security

The application uses JWT for authentication. Include the JWT token in the Authorization header for protected endpoints:
```
Authorization: Bearer <token>
```

## License

This project is licensed under the MIT License. 