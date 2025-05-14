package com.reservasala.usuario.domain.event;

public interface UsuarioEventPublisher {
    void publish(UsuarioEvent event);
} 