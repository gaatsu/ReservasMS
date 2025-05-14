package com.reservasala.usuario.domain.event;

import com.reservasala.usuario.domain.model.Usuario;

public class UsuarioDeletadoEvent implements UsuarioEvent {
    private final Usuario usuario;

    public UsuarioDeletadoEvent(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }
} 