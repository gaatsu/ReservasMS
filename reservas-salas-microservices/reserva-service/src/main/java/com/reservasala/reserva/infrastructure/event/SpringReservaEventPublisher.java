package com.reservasala.reserva.infrastructure.event;

import com.reservasala.reserva.domain.event.ReservaEvent;
import com.reservasala.reserva.domain.event.ReservaEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringReservaEventPublisher implements ReservaEventPublisher {
    
    private final ApplicationEventPublisher publisher;

    public SpringReservaEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(ReservaEvent event) {
        publisher.publishEvent(event);
    }
} 