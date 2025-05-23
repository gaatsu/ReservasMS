import React, { useState, useEffect } from 'react';
import {
  Typography,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  IconButton,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid
} from '@mui/material';
import { Add as AddIcon, Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import ptBR from 'date-fns/locale/pt-BR';
import axios from 'axios';

interface Reserva {
  id: number;
  salaId: number;
  usuarioId: number;
  dataHora: string;
  sala: {
    id: number;
    nome: string;
  };
  usuario: {
    id: number;
    nome: string;
  };
}

interface Sala {
  id: number;
  nome: string;
}

interface Usuario {
  id: number;
  nome: string;
}

const Reservas = () => {
  const [reservas, setReservas] = useState<Reserva[]>([]);
  const [salas, setSalas] = useState<Sala[]>([]);
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [open, setOpen] = useState(false);
  const [editingReserva, setEditingReserva] = useState<Reserva | null>(null);
  const [formData, setFormData] = useState({
    salaId: '',
    usuarioId: '',
    dataHora: new Date()
  });

  const fetchReservas = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/reservas');
      setReservas(response.data);
    } catch (error) {
      console.error('Erro ao buscar reservas:', error);
    }
  };

  const fetchSalas = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/salas');
      setSalas(response.data);
    } catch (error) {
      console.error('Erro ao buscar salas:', error);
    }
  };

  const fetchUsuarios = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/usuarios');
      setUsuarios(response.data);
    } catch (error) {
      console.error('Erro ao buscar usuários:', error);
    }
  };

  useEffect(() => {
    fetchReservas();
    fetchSalas();
    fetchUsuarios();
  }, []);

  const handleOpen = (reserva?: Reserva) => {
    if (reserva) {
      setEditingReserva(reserva);
      setFormData({
        salaId: reserva.salaId.toString(),
        usuarioId: reserva.usuarioId.toString(),
        dataHora: new Date(reserva.dataHora)
      });
    } else {
      setEditingReserva(null);
      setFormData({
        salaId: '',
        usuarioId: '',
        dataHora: new Date()
      });
    }
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditingReserva(null);
    setFormData({
      salaId: '',
      usuarioId: '',
      dataHora: new Date()
    });
  };

  const handleSubmit = async () => {
    try {
      const data = {
        ...formData,
        salaId: parseInt(formData.salaId),
        usuarioId: parseInt(formData.usuarioId)
      };
      
      if (editingReserva) {
        await axios.put(`http://localhost:8080/api/reservas/${editingReserva.id}`, data);
      } else {
        await axios.post('http://localhost:8080/api/reservas', data);
      }
      handleClose();
      fetchReservas();
    } catch (error) {
      console.error('Erro ao salvar reserva:', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir esta reserva?')) {
      try {
        await axios.delete(`http://localhost:8080/api/reservas/${id}`);
        fetchReservas();
      } catch (error) {
        console.error('Erro ao excluir reserva:', error);
      }
    }
  };

  const formatDateTime = (dateTime: string) => {
    return new Date(dateTime).toLocaleString('pt-BR');
  };

  return (
    <div>
      <Typography variant="h4" component="h1" gutterBottom>
        Reservas
      </Typography>
      <Button
        variant="contained"
        color="primary"
        startIcon={<AddIcon />}
        onClick={() => handleOpen()}
        sx={{ mb: 2 }}
      >
        Nova Reserva
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Sala</TableCell>
              <TableCell>Usuário</TableCell>
              <TableCell>Data/Hora</TableCell>
              <TableCell align="right">Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reservas.map((reserva) => (
              <TableRow key={reserva.id}>
                <TableCell>{reserva.sala.nome}</TableCell>
                <TableCell>{reserva.usuario.nome}</TableCell>
                <TableCell>{formatDateTime(reserva.dataHora)}</TableCell>
                <TableCell align="right">
                  <IconButton onClick={() => handleOpen(reserva)} color="primary">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(reserva.id)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editingReserva ? 'Editar Reserva' : 'Nova Reserva'}
        </DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12}>
              <FormControl fullWidth>
                <InputLabel>Sala</InputLabel>
                <Select
                  value={formData.salaId}
                  label="Sala"
                  onChange={(e) => setFormData({ ...formData, salaId: e.target.value })}
                >
                  {salas.map((sala) => (
                    <MenuItem key={sala.id} value={sala.id}>
                      {sala.nome}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12}>
              <FormControl fullWidth>
                <InputLabel>Usuário</InputLabel>
                <Select
                  value={formData.usuarioId}
                  label="Usuário"
                  onChange={(e) => setFormData({ ...formData, usuarioId: e.target.value })}
                >
                  {usuarios.map((usuario) => (
                    <MenuItem key={usuario.id} value={usuario.id}>
                      {usuario.nome}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12}>
              <LocalizationProvider dateAdapter={AdapterDateFns} adapterLocale={ptBR}>
                <DateTimePicker
                  label="Data/Hora"
                  value={formData.dataHora}
                  onChange={(newValue) => setFormData({ ...formData, dataHora: newValue || new Date() })}
                  sx={{ width: '100%' }}
                />
              </LocalizationProvider>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancelar</Button>
          <Button onClick={handleSubmit} color="primary">
            Salvar
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Reservas; 