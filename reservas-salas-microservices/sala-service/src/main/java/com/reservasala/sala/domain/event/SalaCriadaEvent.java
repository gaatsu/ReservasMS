package com.reservasala.sala.domain.event;

import com.reservasala.sala.domain.model.Sala;

public class SalaCriadaEvent implements SalaEvent {
    private final Sala sala;

    public SalaCriadaEvent(Sala sala) {
        this.sala = sala;
    }

    @Override
    public Sala getSala() {
        return sala;
    }
} 