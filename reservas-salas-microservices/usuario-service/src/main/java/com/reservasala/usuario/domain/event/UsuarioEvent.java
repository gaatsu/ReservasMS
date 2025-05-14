package com.reservasala.usuario.domain.event;

import com.reservasala.usuario.domain.model.Usuario;

public interface UsuarioEvent {
    Usuario getUsuario();
} 