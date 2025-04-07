import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';

export const Navbar: React.FC = () => {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    Workout Tracker
                </Typography>
                <Box>
                    <Button color="inherit" component={RouterLink} to="/">
                        Dashboard
                    </Button>
                    <Button color="inherit" component={RouterLink} to="/exercises">
                        Exercises
                    </Button>
                    <Button color="inherit" component={RouterLink} to="/workouts">
                        Workouts
                    </Button>
                    <Button color="inherit" component={RouterLink} to="/profile">
                        Profile
                    </Button>
                </Box>
            </Toolbar>
        </AppBar>
    );
}; 