package com.reservasala.reserva.domain.event;

import com.reservasala.reserva.domain.model.Reserva;

public class ReservaDeletadaEvent implements ReservaEvent {
    private final Long reservaId;

    public ReservaDeletadaEvent(Reserva reserva) {
        this.reservaId = reserva.getId();
    }

    @Override
    public Long getReservaId() {
        return reservaId;
    }
} 