import React, { useEffect, useState } from 'react';
import { Container, Grid, Paper, Typography } from '@mui/material';
import { Navbar } from '../components/Navbar';
import { api } from '../services/api';

export const Statistics: React.FC = () => {
    const [statistics, setStatistics] = useState<any>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        const fetchStatistics = async () => {
            try {
                const startDate = new Date();
                startDate.setMonth(startDate.getMonth() - 1); // Un mois en arrière
                const endDate = new Date();

                console.log('Requesting statistics with startDate:', startDate.toISOString(), 'endDate:', endDate.toISOString());

                const response = await api.get('/reports/progress', {
                    params: {
                        startDate: startDate.toISOString(),
                        endDate: endDate.toISOString(),
                    },
                });
                console.log('Statistics data:', response.data);
                setStatistics(response.data);
            } catch (error) {
                console.error('Error fetching statistics:', error);
                if (error instanceof Error) {
                    setError(error);
                } else {
                    setError(new Error('An unknown error occurred'));
                }
            } finally {
                setLoading(false);
            }
        };

        fetchStatistics();
    }, []);

    if (loading) return <Typography>Loading...</Typography>;
    if (error) return <Typography>Error loading statistics.</Typography>;

    return (
        <>
            <Navbar />
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Typography component="h1" variant="h4" color="primary" gutterBottom>
                    Workout Statistics
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2 }}>
                            {statistics && (
                                <div>
                                    <Typography variant="h6">Total Workouts: {statistics.totalWorkouts}</Typography>
                                    <Typography variant="h6">Completed Workouts: {statistics.completedWorkouts}</Typography>
                                    <Typography variant="h6">Average Workouts Per Week: {statistics.averageWorkoutsPerWeek}</Typography>
                                </div>
                            )}
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
}; 