package com.reservasala.reserva.domain.model;

import lombok.Value;

@Value
public class UsuarioId {
    Long value;

    public UsuarioId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser um valor positivo");
        }
        this.value = value;
    }
} 