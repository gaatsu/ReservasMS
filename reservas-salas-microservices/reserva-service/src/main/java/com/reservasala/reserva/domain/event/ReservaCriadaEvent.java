package com.reservasala.reserva.domain.event;

import com.reservasala.reserva.domain.model.Reserva;

public class ReservaCriadaEvent implements ReservaEvent {
    private final Reserva reserva;

    public ReservaCriadaEvent(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public Reserva getReserva() {
        return reserva;
    }
} 