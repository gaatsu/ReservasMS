import React from 'react';
import { Typography, Grid, Card, CardContent, CardActions, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { People, MeetingRoom, CalendarMonth } from '@mui/icons-material';

const Home = () => {
  const navigate = useNavigate();

  const features = [
    {
      title: 'Usuários',
      description: 'Gerencie os usuários do sistema',
      icon: <People fontSize="large" />,
      path: '/usuarios'
    },
    {
      title: 'Salas',
      description: 'Gerencie as salas disponíveis',
      icon: <MeetingRoom fontSize="large" />,
      path: '/salas'
    },
    {
      title: 'Reservas',
      description: 'Gerencie as reservas de salas',
      icon: <CalendarMonth fontSize="large" />,
      path: '/reservas'
    }
  ];

  return (
    <div>
      <Typography variant="h4" component="h1" gutterBottom>
        Bem-vindo ao Sistema de Reserva de Salas
      </Typography>
      <Typography variant="body1" paragraph>
        Este sistema permite gerenciar usuários, salas e reservas de forma simples e eficiente.
      </Typography>
      <Grid container spacing={3} sx={{ mt: 2 }}>
        {features.map((feature) => (
          <Grid item xs={12} sm={6} md={4} key={feature.title}>
            <Card>
              <CardContent>
                <div style={{ textAlign: 'center', marginBottom: 16 }}>
                  {feature.icon}
                </div>
                <Typography variant="h5" component="h2" gutterBottom>
                  {feature.title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {feature.description}
                </Typography>
              </CardContent>
              <CardActions>
                <Button
                  size="small"
                  color="primary"
                  onClick={() => navigate(feature.path)}
                >
                  Acessar
                </Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>
    </div>
  );
};

export default Home; 