import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { Container } from '@mui/material';
import theme from './theme';
import Navbar from './components/Navbar';
import AppRoutes from './routes';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <BrowserRouter>
        <Navbar />
        <Container sx={{ mt: 4 }}>
          <AppRoutes />
        </Container>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App; 