package com.reservasala.reserva.domain.event;

public interface ReservaEventPublisher {
    void publish(ReservaEvent event);
} 