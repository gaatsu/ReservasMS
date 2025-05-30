package com.reservasala.reserva.domain.event;

import com.reservasala.reserva.domain.model.Reserva;

public class ReservaDeletadaEvent implements ReservaEvent {
    private final Reserva reserva;

    public ReservaDeletadaEvent(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public Reserva getReserva() {
        return reserva;
    }
} 