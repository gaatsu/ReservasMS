package com.reservasala.reserva.domain.event;

import com.reservasala.reserva.domain.model.Reserva;

public interface ReservaEvent {
    Reserva getReserva();
} 