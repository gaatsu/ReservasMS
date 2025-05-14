package com.reservasala.sala.domain.event;

import com.reservasala.sala.domain.model.Sala;

public class SalaDeletadaEvent implements SalaEvent {
    private final Sala sala;

    public SalaDeletadaEvent(Sala sala) {
        this.sala = sala;
    }

    @Override
    public Sala getSala() {
        return sala;
    }
} 