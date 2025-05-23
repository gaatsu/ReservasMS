import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { CalendarMonth, People, MeetingRoom, Home } from '@mui/icons-material';

const Navbar = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Sistema de Reserva de Salas
        </Typography>
        <Box>
          <Button
            color="inherit"
            component={RouterLink}
            to="/"
            startIcon={<Home />}
          >
            Home
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/usuarios"
            startIcon={<People />}
          >
            Usuários
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/salas"
            startIcon={<MeetingRoom />}
          >
            Salas
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/reservas"
            startIcon={<CalendarMonth />}
          >
            Reservas
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar; 