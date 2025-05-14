package com.reservasala.reserva.domain.event;

import com.reservasala.reserva.domain.model.Reserva;

public class ReservaAtualizadaEvent implements ReservaEvent {
    private final Reserva reserva;

    public ReservaAtualizadaEvent(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public Reserva getReserva() {
        return reserva;
    }
} 