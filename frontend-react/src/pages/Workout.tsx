import React, { useEffect, useState } from 'react';
import { Container, Grid, Paper, Typography, List, ListItem, ListItemText } from '@mui/material';
import { Navbar } from '../components/Navbar';
import { api } from '../services/api';

export const Workout: React.FC = () => {
    const [workouts, setWorkouts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        const fetchWorkouts = async () => {
            try {
                const response = await api.get('/workouts');
                console.log('Workouts data:', response.data);
                setWorkouts(response.data);
            } catch (error) {
                console.error('Error fetching workouts:', error);
                if (error instanceof Error) {
                    setError(error);
                } else {
                    setError(new Error('An unknown error occurred'));
                }
            } finally {
                setLoading(false);
            }
        };

        fetchWorkouts();
    }, []);

    if (loading) return <Typography>Loading...</Typography>;
    if (error) return <Typography>Error loading workouts.</Typography>;

    return (
        <>
            <Navbar />
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Typography component="h1" variant="h4" color="primary" gutterBottom>
                    Workouts
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2 }}>
                            <List>
                                {workouts.map((workout: any) => (
                                    <ListItem key={workout.id}>
                                        <ListItemText
                                            primary={workout.name}
                                            secondary={workout.description}
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