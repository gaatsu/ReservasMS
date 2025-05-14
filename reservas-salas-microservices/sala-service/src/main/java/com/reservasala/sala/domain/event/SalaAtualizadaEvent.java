package com.reservasala.sala.domain.event;

import com.reservasala.sala.domain.model.Sala;

public class SalaAtualizadaEvent implements SalaEvent {
    private final Sala sala;

    public SalaAtualizadaEvent(Sala sala) {
        this.sala = sala;
    }

    @Override
    public Sala getSala() {
        return sala;
    }
} 