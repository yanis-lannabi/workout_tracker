import React, { useEffect, useState } from 'react';
import { Container, Grid, Paper, Typography, List, ListItem, ListItemText, Chip, Box } from '@mui/material';
import { Navbar } from '../components/Navbar';
import { AddExerciseForm } from '../components/AddExerciseForm';
import { api } from '../services/api';
import { Exercise, ExerciseCategory, MuscleGroup } from '../types';

export const Exercises: React.FC = () => {
    const [exercises, setExercises] = useState<Exercise[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    const fetchExercises = async () => {
        try {
            const response = await api.get('/exercises');
            setExercises(response.data);
        } catch (error) {
            console.error('Error fetching exercises:', error);
            if (error instanceof Error) {
                setError(error);
            } else {
                setError(new Error('An unknown error occurred'));
            }
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchExercises();
    }, []);

    if (loading) return <Typography>Loading...</Typography>;
    if (error) return <Typography>Error loading exercises.</Typography>;

    return (
        <>
            <Navbar />
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Typography component="h1" variant="h4" color="primary" gutterBottom>
                    Exercises
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={4}>
                        <AddExerciseForm onExerciseAdded={fetchExercises} />
                    </Grid>
                    <Grid item xs={12} md={8}>
                        <Paper sx={{ p: 2 }}>
                            <List>
                                {exercises.map((exercise) => (
                                    <ListItem key={exercise.id} divider>
                                        <ListItemText
                                            primary={exercise.name}
                                            secondary={
                                                <Box>
                                                    <Typography variant="body2" color="text.secondary" paragraph>
                                                        {exercise.description}
                                                    </Typography>
                                                    <Box sx={{ display: 'flex', gap: 1 }}>
                                                        <Chip
                                                            label={exercise.category}
                                                            color="primary"
                                                            size="small"
                                                        />
                                                        <Chip
                                                            label={exercise.muscleGroup}
                                                            color="secondary"
                                                            size="small"
                                                        />
                                                    </Box>
                                                </Box>
                                            }
                                        />
                                    </ListItem>
                                ))}
                            </List>
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
}; 