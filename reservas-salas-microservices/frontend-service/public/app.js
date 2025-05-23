// Configuração da API base (usando proxy do nginx)
const API_BASE = '';

// Estado da aplicação
let salas = [];
let usuarios = [];
let reservas = [];

// Funções utilitárias
function showAlert(message, type = 'success') {
    const alertDiv = document.getElementById('alertMessage');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    alertDiv.style.display = 'block';
    
    // Auto-hide após 5 segundos
    setTimeout(() => {
        alertDiv.style.display = 'none';
    }, 5000);
}

function showLoading() {
    document.getElementById('loading').style.display = 'block';
}

function hideLoading() {
    document.getElementById('loading').style.display = 'none';
}

function formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return '';
    try {
        const date = new Date(dateTimeStr);
        return date.toLocaleString('pt-BR');
    } catch (e) {
        return dateTimeStr;
    }
}

// Funções de API
async function fetchSalas() {
    try {
        const response = await fetch(`${API_BASE}/api/salas`);
        if (!response.ok) throw new Error('Erro ao buscar salas');
        return await response.json();
    } catch (error) {
        console.error('Erro ao buscar salas:', error);
        showAlert('Erro ao carregar salas: ' + error.message, 'danger');
        return [];
    }
}

async function fetchUsuarios() {
    try {
        const response = await fetch(`${API_BASE}/api/usuarios`);
        if (!response.ok) throw new Error('Erro ao buscar usuários');
        return await response.json();
    } catch (error) {
        console.error('Erro ao buscar usuários:', error);
        showAlert('Erro ao carregar usuários: ' + error.message, 'danger');
        return [];
    }
}

async function fetchReservas() {
    try {
        const response = await fetch(`${API_BASE}/api/reservas`);
        if (!response.ok) throw new Error('Erro ao buscar reservas');
        return await response.json();
    } catch (error) {
        console.error('Erro ao buscar reservas:', error);
        showAlert('Erro ao carregar reservas: ' + error.message, 'danger');
        return [];
    }
}

async function criarReserva(reservaData) {
    try {
        const response = await fetch(`${API_BASE}/api/reservas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reservaData)
        });
        
        if (!response.ok) throw new Error('Erro ao criar reserva');
        return true;
    } catch (error) {
        console.error('Erro ao criar reserva:', error);
        showAlert('Erro ao criar reserva: ' + error.message, 'danger');
        return false;
    }
}

async function criarUsuario(usuarioData) {
    try {
        const response = await fetch(`${API_BASE}/api/usuarios`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuarioData)
        });
        
        if (!response.ok) throw new Error('Erro ao criar usuário');
        return true;
    } catch (error) {
        console.error('Erro ao criar usuário:', error);
        showAlert('Erro ao criar usuário: ' + error.message, 'danger');
        return false;
    }
}

async function criarSala(salaData) {
    try {
        const response = await fetch(`${API_BASE}/api/salas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(salaData)
        });
        
        if (!response.ok) throw new Error('Erro ao criar sala');
        return true;
    } catch (error) {
        console.error('Erro ao criar sala:', error);
        showAlert('Erro ao criar sala: ' + error.message, 'danger');
        return false;
    }
}

async function deletarReserva(id) {
    try {
        const response = await fetch(`${API_BASE}/api/reservas/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Erro ao deletar reserva');
        return true;
    } catch (error) {
        console.error('Erro ao deletar reserva:', error);
        showAlert('Erro ao deletar reserva: ' + error.message, 'danger');
        return false;
    }
}

async function deletarSala(id) {
    try {
        const response = await fetch(`${API_BASE}/api/salas/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Erro ao deletar sala');
        return true;
    } catch (error) {
        console.error('Erro ao deletar sala:', error);
        showAlert('Erro ao deletar sala: ' + error.message, 'danger');
        return false;
    }
}

async function deletarUsuario(id) {
    try {
        const response = await fetch(`${API_BASE}/api/usuarios/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Erro ao deletar usuário');
        return true;
    } catch (error) {
        console.error('Erro ao deletar usuário:', error);
        showAlert('Erro ao deletar usuário: ' + error.message, 'danger');
        return false;
    }
}

async function editarSala(id, salaData) {
    try {
        const response = await fetch(`${API_BASE}/api/salas/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(salaData)
        });
        
        if (!response.ok) throw new Error('Erro ao editar sala');
        return true;
    } catch (error) {
        console.error('Erro ao editar sala:', error);
        showAlert('Erro ao editar sala: ' + error.message, 'danger');
        return false;
    }
}

async function editarUsuario(id, usuarioData) {
    try {
        const response = await fetch(`${API_BASE}/api/usuarios/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuarioData)
        });
        
        if (!response.ok) throw new Error('Erro ao editar usuário');
        return true;
    } catch (error) {
        console.error('Erro ao editar usuário:', error);
        showAlert('Erro ao editar usuário: ' + error.message, 'danger');
        return false;
    }
}

// Funções de renderização
function populateSelectOptions() {
    const salaSelect = document.getElementById('salaId');
    const usuarioSelect = document.getElementById('usuarioId');
    
    // Limpar opções existentes
    salaSelect.innerHTML = '<option value="">Selecione uma sala</option>';
    usuarioSelect.innerHTML = '<option value="">Selecione um usuário</option>';
    
    // Adicionar salas
    salas.forEach(sala => {
        const option = document.createElement('option');
        option.value = sala.id;
        option.textContent = `${sala.nome} (Capacidade: ${sala.capacidade})`;
        salaSelect.appendChild(option);
    });
    
    // Adicionar usuários
    usuarios.forEach(usuario => {
        const option = document.createElement('option');
        option.value = usuario.id;
        option.textContent = `${usuario.nome} (${usuario.email})`;
        usuarioSelect.appendChild(option);
    });
}

function renderReservas() {
    const tbody = document.getElementById('reservasTableBody');
    tbody.innerHTML = '';
    
    reservas.forEach(reserva => {
        const sala = salas.find(s => s.id == reserva.salaId);
        const usuario = usuarios.find(u => u.id == reserva.usuarioId);
        
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${sala ? sala.nome : 'Sala não encontrada'}</td>
            <td>${usuario ? usuario.nome : 'Usuário não encontrado'}</td>
            <td>${formatDateTime(reserva.dataHora)}</td>
            <td>
                <button class="btn btn-danger btn-sm btn-icon" onclick="handleDeleteReserva(${reserva.id})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function renderSalas() {
    const tbody = document.getElementById('salasTableBody');
    tbody.innerHTML = '';
    
    salas.forEach(sala => {
        const temReserva = reservas.some(r => r.salaId == sala.id);
        const disponivel = !temReserva;
        
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${sala.nome}</td>
            <td>${sala.capacidade}</td>
            <td>
                <span class="${disponivel ? 'sala-disponivel' : 'sala-ocupada'}">
                    ${disponivel ? 'Disponível' : 'Ocupada'}
                </span>
            </td>
            <td>
                <button class="btn btn-primary btn-sm btn-icon" onclick="handleEditSala(${sala.id}, '${sala.nome}', ${sala.capacidade})">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm btn-icon" onclick="handleDeleteSala(${sala.id})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function renderUsuarios() {
    const tbody = document.getElementById('usuariosTableBody');
    tbody.innerHTML = '';
    
    usuarios.forEach(usuario => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${usuario.nome}</td>
            <td>${usuario.email}</td>
            <td>
                <button class="btn btn-primary btn-sm btn-icon" onclick="handleEditUsuario(${usuario.id}, '${usuario.nome}', '${usuario.email}')">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm btn-icon" onclick="handleDeleteUsuario(${usuario.id})">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Handlers de eventos
async function handleDeleteReserva(id) {
    if (confirm('Tem certeza que deseja deletar esta reserva?')) {
        if (await deletarReserva(id)) {
            showAlert('Reserva deletada com sucesso!');
            loadData();
        }
    }
}

async function handleDeleteSala(id) {
    if (confirm('Tem certeza que deseja deletar esta sala?')) {
        if (await deletarSala(id)) {
            showAlert('Sala deletada com sucesso!');
            loadData();
        }
    }
}

async function handleDeleteUsuario(id) {
    if (confirm('Tem certeza que deseja deletar este usuário?')) {
        if (await deletarUsuario(id)) {
            showAlert('Usuário deletado com sucesso!');
            loadData();
        }
    }
}

async function handleEditSala(id, nome, capacidade) {
    const novoNome = prompt('Digite o novo nome da sala:', nome);
    const novaCapacidade = prompt('Digite a nova capacidade da sala:', capacidade);
    
    if (novoNome && novaCapacidade) {
        const salaData = {
            id: parseInt(id),
            nome: novoNome,
            capacidade: parseInt(novaCapacidade)
        };
        
        if (await editarSala(id, salaData)) {
            showAlert('Sala atualizada com sucesso!');
            loadData();
        }
    }
}

async function handleEditUsuario(id, nome, email) {
    const novoNome = prompt('Digite o novo nome do usuário:', nome);
    const novoEmail = prompt('Digite o novo email do usuário:', email);
    
    if (novoNome && novoEmail) {
        const usuarioData = {
            id: parseInt(id),
            nome: novoNome,
            email: novoEmail
        };
        
        if (await editarUsuario(id, usuarioData)) {
            showAlert('Usuário atualizado com sucesso!');
            loadData();
        }
    }
}

// Função principal para carregar dados
async function loadData() {
    showLoading();
    
    try {
        [salas, usuarios, reservas] = await Promise.all([
            fetchSalas(),
            fetchUsuarios(),
            fetchReservas()
        ]);
        
        populateSelectOptions();
        renderReservas();
        renderSalas();
        renderUsuarios();
    } catch (error) {
        console.error('Erro ao carregar dados:', error);
        showAlert('Erro ao carregar dados da aplicação', 'danger');
    } finally {
        hideLoading();
    }
}

// Event listeners para formulários
document.addEventListener('DOMContentLoaded', function() {
    // Carregar dados iniciais
    loadData();
    
    // Formulário de reserva
    document.getElementById('formReserva').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const formData = new FormData(this);
        const reservaData = {
            salaId: parseInt(formData.get('salaId')),
            usuarioId: parseInt(formData.get('usuarioId')),
            dataHora: formData.get('dataHora') + ':00'
        };
        
        if (await criarReserva(reservaData)) {
            showAlert('Reserva criada com sucesso!');
            this.reset();
            loadData();
        }
    });
    
    // Formulário de usuário
    document.getElementById('formUsuario').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const formData = new FormData(this);
        const usuarioData = {
            nome: formData.get('nome'),
            email: formData.get('email')
        };
        
        if (await criarUsuario(usuarioData)) {
            showAlert('Usuário criado com sucesso!');
            this.reset();
            loadData();
        }
    });
    
    // Formulário de sala
    document.getElementById('formSala').addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const formData = new FormData(this);
        const salaData = {
            nome: formData.get('nome'),
            capacidade: parseInt(formData.get('capacidade'))
        };
        
        if (await criarSala(salaData)) {
            showAlert('Sala criada com sucesso!');
            this.reset();
            loadData();
        }
    });
}); 