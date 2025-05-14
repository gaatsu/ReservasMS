package com.reservasala.sala.domain.event;

public interface SalaEventPublisher {
    void publish(SalaEvent event);
} 