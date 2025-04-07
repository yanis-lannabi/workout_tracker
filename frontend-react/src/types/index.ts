export interface User {
    id: number;
    username: string;
    email: string;
    firstName?: string;
    lastName?: string;
}

export interface Exercise {
    id: number;
    name: string;
    description: string;
    category: string;
    muscleGroup: string;
}

export interface Workout {
    id: number;
    name: string;
    description?: string;
    date: string;
    status: 'PLANNED' | 'IN_PROGRESS' | 'COMPLETED';
    userId: number;
}

export interface WorkoutExercise {
    id: number;
    workoutId: number;
    exerciseId: number;
    sets: number;
    reps: number;
    weight?: number;
    duration?: number;
    notes?: string;
    exercise?: Exercise;
} 