import React, { useState } from 'react';
import {
  Box,
  Button,
  Paper,
  TextField,
  Typography
} from '@mui/material';
import { api } from '../services/api';

interface AddWorkoutFormProps {
  onWorkoutAdded: () => void;
}

export const AddWorkoutForm: React.FC<AddWorkoutFormProps> = ({ onWorkoutAdded }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    scheduledDateTime: new Date().toISOString().slice(0, 16),
    comments: ''
  });

  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/workouts', formData);
      setFormData({
        name: '',
        description: '',
        scheduledDateTime: new Date().toISOString().slice(0, 16),
        comments: ''
      });
      onWorkoutAdded();
    } catch (error) {
      console.error('Error adding workout:', error);
    }
  };

  return (
    <Paper sx={{ p: 3, mb: 3 }}>
      <Typography variant="h6" gutterBottom>
        Add New Workout
      </Typography>
      <Box component="form" onSubmit={handleSubmit} noValidate>
        <TextField
          margin="normal"
          required
          fullWidth
          label="Workout Name"
          name="name"
          value={formData.name}
          onChange={handleTextChange}
        />
        <TextField
          margin="normal"
          fullWidth
          label="Description"
          name="description"
          multiline
          rows={3}
          value={formData.description}
          onChange={handleTextChange}
        />
        <TextField
          margin="normal"
          fullWidth
          label="Scheduled Date & Time"
          name="scheduledDateTime"
          type="datetime-local"
          value={formData.scheduledDateTime}
          onChange={handleTextChange}
          InputLabelProps={{
            shrink: true,
          }}
        />
        <TextField
          margin="normal"
          fullWidth
          label="Comments"
          name="comments"
          multiline
          rows={2}
          value={formData.comments}
          onChange={handleTextChange}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3 }}
        >
          Add Workout
        </Button>
      </Box>
    </Paper>
  );
}; 