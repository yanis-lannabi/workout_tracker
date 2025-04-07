import React, { useEffect, useState } from 'react';
import { Container, Grid, Paper, Typography, List, ListItem, ListItemText } from '@mui/material';
import { Navbar } from '../components/Navbar';
import { api } from '../services/api';

export const Exercises: React.FC = () => {
    const [exercises, setExercises] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        const fetchExercises = async () => {
            try {
                const response = await api.get('/exercises');
                console.log('Exercises data:', response.data);
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
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2 }}>
                            <List>
                                {exercises.map((exercise: any) => (
                                    <ListItem key={exercise.id}>
                                        <ListItemText
                                            primary={exercise.name}
                                            secondary={exercise.description}
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