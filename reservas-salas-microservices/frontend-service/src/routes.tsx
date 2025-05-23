import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Usuarios from './pages/Usuarios';
import Salas from './pages/Salas';
import Reservas from './pages/Reservas';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/usuarios" element={<Usuarios />} />
      <Route path="/salas" element={<Salas />} />
      <Route path="/reservas" element={<Reservas />} />
    </Routes>
  );
};

export default AppRoutes; 