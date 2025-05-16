import React, { useEffect, useState } from 'react';
import {
    Box,
    Chip,
    Container,
    Grid,
    Paper,
    Typography,
    CircularProgress,
    Alert,
    Menu,
    MenuItem,
    IconButton
} from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { api } from '../services/api';
import { AddWorkoutForm } from '../components/AddWorkoutForm';
import { Navbar } from '../components/Navbar';

interface Workout {
    id: number;
    name: string;
    description: string;
    scheduledDateTime: string;
    status: 'PLANNED' | 'COMPLETED' | 'CANCELLED';
    comments?: string;
}

export const WorkoutPage: React.FC = () => {
    const [workouts, setWorkouts] = useState<Workout[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [selectedWorkoutId, setSelectedWorkoutId] = useState<number | null>(null);

    const fetchWorkouts = async () => {
        try {
            setLoading(true);
            const response = await api.get<Workout[]>('/workouts');
            setWorkouts(response.data);
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

    const handleMenuClick = (event: React.MouseEvent<HTMLElement>, workoutId: number) => {
        setAnchorEl(event.currentTarget);
        setSelectedWorkoutId(workoutId);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
        setSelectedWorkoutId(null);
    };

    const handleStatusChange = async (newStatus: Workout['status']) => {
        if (!selectedWorkoutId) return;

        try {
            await api.patch(`/workouts/${selectedWorkoutId}/status`, { status: newStatus });
            await fetchWorkouts();
            handleMenuClose();
        } catch (err) {
            setError('Failed to update workout status');
            console.error('Error updating workout status:', err);
        }
    };

    const getStatusColor = (status: Workout['status']): { color: 'success' | 'error' | 'primary', backgroundColor: string, fontWeight: string } => {
        switch (status) {
            case 'COMPLETED':
                return {
                    color: 'success',
                    backgroundColor: 'success.light',
                    fontWeight: 'bold'
                };
            case 'CANCELLED':
                return {
                    color: 'error',
                    backgroundColor: 'error.light',
                    fontWeight: 'bold'
                };
            default:
                return {
                    color: 'primary',
                    backgroundColor: 'primary.light',
                    fontWeight: 'bold'
                };
        }
    };

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="80vh">
                <CircularProgress />
            </Box>
        );
    }
    return (
        <>
            <Navbar />
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={4}>
                        <AddWorkoutForm onWorkoutAdded={fetchWorkouts} />
                    </Grid>
                    <Grid item xs={12} md={8}>
                        {error && (
                            <Alert severity="error" sx={{ mb: 2 }}>
                                {error}
                            </Alert>
                        )}
                        {workouts.map((workout) => (
                            <Paper key={workout.id} sx={{ p: 2, mb: 2 }}>
                                <Box display="flex" justifyContent="space-between" alignItems="flex-start">
                                    <Typography variant="h6" component="h2">
                                        {workout.name}
                                    </Typography>
                                    <Box display="flex" alignItems="center" gap={1}>
                                        <Chip
                                            label={workout.status}
                                            color={getStatusColor(workout.status).color}
                                            size="small"
                                            sx={{
                                                backgroundColor: getStatusColor(workout.status).backgroundColor,
                                                fontWeight: getStatusColor(workout.status).fontWeight,
                                                '& .MuiChip-label': {
                                                    color: 'white'
                                                }
                                            }}
                                        />
                                        <IconButton
                                            size="small"
                                            onClick={(e) => handleMenuClick(e, workout.id)}
                                        >
                                            <MoreVertIcon />
                                        </IconButton>
                                    </Box>
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
                        ))}
                    </Grid>
                </Grid>
            </Container>
            <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
            >
                <MenuItem onClick={() => handleStatusChange('PLANNED')}>Mark as Planned</MenuItem>
                <MenuItem onClick={() => handleStatusChange('COMPLETED')}>Mark as Completed</MenuItem>
                <MenuItem onClick={() => handleStatusChange('CANCELLED')}>Mark as Cancelled</MenuItem>
            </Menu>
        </>
    );
};