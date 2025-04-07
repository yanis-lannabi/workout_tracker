import React from 'react';
import { Container, Grid, Paper, Typography } from '@mui/material';
import { Navbar } from '../components/Navbar';

export const Dashboard: React.FC = () => {
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
                            {/* TODO: Add recent workouts list */}
                        </Paper>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                            <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                Statistics
                            </Typography>
                            {/* TODO: Add statistics */}
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
}; 