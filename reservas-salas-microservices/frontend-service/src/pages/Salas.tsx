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
  MenuItem
} from '@mui/material';
import { Add as AddIcon, Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import axios from 'axios';

interface Sala {
  id: number;
  nome: string;
  capacidade: number;
  tipo: string;
  localizacao: string;
}

const Salas = () => {
  const [salas, setSalas] = useState<Sala[]>([]);
  const [open, setOpen] = useState(false);
  const [editingSala, setEditingSala] = useState<Sala | null>(null);
  const [formData, setFormData] = useState({
    nome: '',
    capacidade: '',
    tipo: '',
    localizacao: ''
  });

  const tiposSala = [
    'Sala de Aula',
    'Laboratório',
    'Auditório',
    'Sala de Reunião',
    'Sala de Conferência'
  ];

  const fetchSalas = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/salas');
      setSalas(response.data);
    } catch (error) {
      console.error('Erro ao buscar salas:', error);
    }
  };

  useEffect(() => {
    fetchSalas();
  }, []);

  const handleOpen = (sala?: Sala) => {
    if (sala) {
      setEditingSala(sala);
      setFormData({
        nome: sala.nome,
        capacidade: sala.capacidade.toString(),
        tipo: sala.tipo,
        localizacao: sala.localizacao
      });
    } else {
      setEditingSala(null);
      setFormData({
        nome: '',
        capacidade: '',
        tipo: '',
        localizacao: ''
      });
    }
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditingSala(null);
    setFormData({
      nome: '',
      capacidade: '',
      tipo: '',
      localizacao: ''
    });
  };

  const handleSubmit = async () => {
    try {
      const data = {
        ...formData,
        capacidade: parseInt(formData.capacidade)
      };
      
      if (editingSala) {
        await axios.put(`http://localhost:8080/api/salas/${editingSala.id}`, data);
      } else {
        await axios.post('http://localhost:8080/api/salas', data);
      }
      handleClose();
      fetchSalas();
    } catch (error) {
      console.error('Erro ao salvar sala:', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir esta sala?')) {
      try {
        await axios.delete(`http://localhost:8080/api/salas/${id}`);
        fetchSalas();
      } catch (error) {
        console.error('Erro ao excluir sala:', error);
      }
    }
  };

  return (
    <div>
      <Typography variant="h4" component="h1" gutterBottom>
        Salas
      </Typography>
      <Button
        variant="contained"
        color="primary"
        startIcon={<AddIcon />}
        onClick={() => handleOpen()}
        sx={{ mb: 2 }}
      >
        Nova Sala
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nome</TableCell>
              <TableCell>Capacidade</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Localização</TableCell>
              <TableCell align="right">Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {salas.map((sala) => (
              <TableRow key={sala.id}>
                <TableCell>{sala.nome}</TableCell>
                <TableCell>{sala.capacidade}</TableCell>
                <TableCell>{sala.tipo}</TableCell>
                <TableCell>{sala.localizacao}</TableCell>
                <TableCell align="right">
                  <IconButton onClick={() => handleOpen(sala)} color="primary">
                    <EditIcon />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(sala.id)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>
          {editingSala ? 'Editar Sala' : 'Nova Sala'}
        </DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Nome"
            fullWidth
            value={formData.nome}
            onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Capacidade"
            type="number"
            fullWidth
            value={formData.capacidade}
            onChange={(e) => setFormData({ ...formData, capacidade: e.target.value })}
          />
          <FormControl fullWidth margin="dense">
            <InputLabel>Tipo</InputLabel>
            <Select
              value={formData.tipo}
              label="Tipo"
              onChange={(e) => setFormData({ ...formData, tipo: e.target.value })}
            >
              {tiposSala.map((tipo) => (
                <MenuItem key={tipo} value={tipo}>
                  {tipo}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
            margin="dense"
            label="Localização"
            fullWidth
            value={formData.localizacao}
            onChange={(e) => setFormData({ ...formData, localizacao: e.target.value })}
          />
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

export default Salas; 