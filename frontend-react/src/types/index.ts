export interface User {
    id: number;
    username: string;
    email: string;
    firstName?: string;
    lastName?: string;
}

export enum ExerciseCategory {
    CARDIO = 'CARDIO',
    STRENGTH = 'STRENGTH',
    FLEXIBILITY = 'FLEXIBILITY',
    BALANCE = 'BALANCE',
    CORE = 'CORE'
}

export enum MuscleGroup {
    CHEST = 'CHEST',
    BACK = 'BACK',
    SHOULDERS = 'SHOULDERS',
    ARMS = 'ARMS',
    ABS = 'ABS',
    LEGS = 'LEGS',
    FULL_BODY = 'FULL_BODY'
}

export interface Exercise {
    id: number;
    name: string;
    description: string;
    category: ExerciseCategory;
    muscleGroup: MuscleGroup;
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