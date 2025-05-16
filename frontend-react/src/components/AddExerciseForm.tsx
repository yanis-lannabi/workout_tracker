import React, { useState } from 'react';
import {
  Box,
  Button,
  TextField,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  Typography,
  Paper,
  SelectChangeEvent
} from '@mui/material';
import { api } from '../services/api';
import { ExerciseCategory, MuscleGroup } from '../types';

interface AddExerciseFormProps {
  onExerciseAdded: () => void;
}

export const AddExerciseForm: React.FC<AddExerciseFormProps> = ({ onExerciseAdded }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    category: '',
    muscleGroup: ''
  });

  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSelectChange = (e: SelectChangeEvent) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/exercises', formData);
      setFormData({
        name: '',
        description: '',
        category: '',
        muscleGroup: ''
      });
      onExerciseAdded();
    } catch (error) {
      console.error('Error adding exercise:', error);
    }
  };

  return (
    <Paper sx={{ p: 3, mb: 3 }}>
      <Typography variant="h6" gutterBottom>
        Add New Exercise
      </Typography>
      <Box component="form" onSubmit={handleSubmit} noValidate>
        <TextField
          margin="normal"
          required
          fullWidth
          label="Exercise Name"
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
        <FormControl fullWidth margin="normal">
          <InputLabel>Category</InputLabel>
          <Select
            name="category"
            value={formData.category}
            label="Category"
            onChange={handleSelectChange}
            required
          >
            {Object.values(ExerciseCategory).map((category) => (
              <MenuItem key={category} value={category}>
                {category}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth margin="normal">
          <InputLabel>Muscle Group</InputLabel>
          <Select
            name="muscleGroup"
            value={formData.muscleGroup}
            label="Muscle Group"
            onChange={handleSelectChange}
            required
          >
            {Object.values(MuscleGroup).map((group) => (
              <MenuItem key={group} value={group}>
                {group}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3 }}
        >
          Add Exercise
        </Button>
      </Box>
    </Paper>
  );
}; 