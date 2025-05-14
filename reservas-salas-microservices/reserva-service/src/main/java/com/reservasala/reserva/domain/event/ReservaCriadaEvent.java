package com.reservasala.reserva.domain.event;

import com.reservasala.reserva.domain.model.Reserva;

public class ReservaCriadaEvent implements ReservaEvent {
    private final Long reservaId;
    private final Long salaId;
    private final Long usuarioId;
    private final String dataHora;

    public ReservaCriadaEvent(Reserva reserva) {
        this.reservaId = reserva.getId();
        this.salaId = reserva.getSalaId();
        this.usuarioId = reserva.getUsuarioId();
        this.dataHora = reserva.getDataHora().toString();
    }

    @Override
    public Long getReservaId() {
        return reservaId;
    }

    public Long getSalaId() {
        return salaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getDataHora() {
        return dataHora;
    }
} 