package com.reservasala.reserva.domain.model;

import lombok.Value;

@Value
public class SalaId {
    Long value;

    public SalaId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("ID da sala deve ser um valor positivo");
        }
        this.value = value;
    }
} 