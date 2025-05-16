import React, { useEffect, useState } from 'react';
import { Container, Grid, Paper, Typography, Box, Chip, CircularProgress, Alert } from '@mui/material';
import { Navbar } from '../components/Navbar';
import { api } from '../services/api';

interface Workout {
    id: number;
    name: string;
    description: string;
    scheduledDateTime: string;
    status: 'PLANNED' | 'COMPLETED' | 'CANCELLED';
    comments?: string;
}

interface Statistics {
    totalWorkouts: number;
    completedWorkouts: number;
    averageWorkoutsPerWeek: number;
}

export const Dashboard: React.FC = () => {
    const [workouts, setWorkouts] = useState<Workout[]>([]);
    const [statistics, setStatistics] = useState<Statistics | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchWorkouts = async () => {
        try {
            setLoading(true);
            const response = await api.get<Workout[]>('/workouts');
            // Trier les workouts par date et prendre les 5 plus récents
            const sortedWorkouts = response.data
                .sort((a, b) => new Date(b.scheduledDateTime).getTime() - new Date(a.scheduledDateTime).getTime())
                .slice(0, 5);
            setWorkouts(sortedWorkouts);

            // Calculer les statistiques
            const totalWorkouts = response.data.length;
            const completedWorkouts = response.data.filter(w => w.status === 'COMPLETED').length;

            // Calculer la moyenne par semaine
            const now = new Date();
            const fourWeeksAgo = new Date(now.getTime() - (4 * 7 * 24 * 60 * 60 * 1000));
            const recentWorkouts = response.data.filter(w => new Date(w.scheduledDateTime) >= fourWeeksAgo);
            const averageWorkoutsPerWeek = recentWorkouts.length / 4;

            setStatistics({
                totalWorkouts,
                completedWorkouts,
                averageWorkoutsPerWeek: Number(averageWorkoutsPerWeek.toFixed(1))
            });

            setError(null);
        } catch (err) {
            setError('Failed to fetch workouts');
            console.error('Error fetching workouts:', err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchWorkouts();
    }, []);

    const getStatusColor = (status: Workout['status']) => {
        switch (status) {
            case 'COMPLETED':
                return 'success';
            case 'CANCELLED':
                return 'error';
            default:
                return 'primary';
        }
    };

    return (
        <>
            <Navbar />
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                            <Typography component="h1" variant="h4" color="primary" gutterBottom>
                                Welcome to Workout Tracker
                            </Typography>
                            <Typography variant="body1">
                                Track your workouts, monitor your progress, and achieve your fitness goals.
                            </Typography>
                        </Paper>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                Recent Workouts
                            </Typography>
                            {loading ? (
                                <Box display="flex" justifyContent="center" p={3}>
                                    <CircularProgress />
                                </Box>
                            ) : error ? (
                                <Alert severity="error">{error}</Alert>
                            ) : workouts.length === 0 ? (
                                <Typography color="text.secondary">No workouts found</Typography>
                            ) : (
                                workouts.map((workout) => (
                                    <Paper key={workout.id} sx={{ p: 2, mb: 2, backgroundColor: 'background.default' }}>
                                        <Box display="flex" justifyContent="space-between" alignItems="flex-start">
                                            <Typography variant="h6" component="h3">
                                                {workout.name}
                                            </Typography>
                                            <Chip
                                                label={workout.status}
                                                color={getStatusColor(workout.status)}
                                                size="small"
                                            />
                                        </Box>
                                        {workout.description && (
                                            <Typography color="text.secondary" sx={{ mt: 1 }}>
                                                {workout.description}
                                            </Typography>
                                        )}
                                        <Box display="flex" gap={1} mt={1}>
                                            <Chip
                                                label={new Date(workout.scheduledDateTime).toLocaleString()}
                                                size="small"
                                                variant="outlined"
                                            />
                                        </Box>
                                        {workout.comments && (
                                            <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                                                {workout.comments}
                                            </Typography>
                                        )}
                                    </Paper>
                                ))
                            )}
                        </Paper>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                Statistics
                            </Typography>
                            {loading ? (
                                <Box display="flex" justifyContent="center" p={3}>
                                    <CircularProgress />
                                </Box>
                            ) : error ? (
                                <Alert severity="error">{error}</Alert>
                            ) : statistics ? (
                                <Box>
                                    <Grid container spacing={2}>
                                        <Grid item xs={12}>
                                            <Paper sx={{ p: 2, backgroundColor: 'primary.light' }}>
                                                <Typography variant="h6" color="white">
                                                    Total Workouts
                                                </Typography>
                                                <Typography variant="h4" color="white">
                                                    {statistics.totalWorkouts}
                                                </Typography>
                                            </Paper>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Paper sx={{ p: 2, backgroundColor: 'success.light' }}>
                                                <Typography variant="h6" color="white">
                                                    Completed Workouts
                                                </Typography>
                                                <Typography variant="h4" color="white">
                                                    {statistics.completedWorkouts}
                                                </Typography>
                                            </Paper>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Paper sx={{ p: 2, backgroundColor: 'secondary.light' }}>
                                                <Typography variant="h6" color="white">
                                                    Average Workouts per Week
                                                </Typography>
                                                <Typography variant="h4" color="white">
                                                    {statistics.averageWorkoutsPerWeek}
                                                </Typography>
                                            </Paper>
                                        </Grid>
                                    </Grid>
                                </Box>
                            ) : (
                                <Typography color="text.secondary">No statistics available</Typography>
                            )}
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
}; 