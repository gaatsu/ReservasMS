package com.reservasala.sala.infrastructure.event;

import com.reservasala.sala.domain.event.SalaEvent;
import com.reservasala.sala.domain.event.SalaEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringSalaEventPublisher implements SalaEventPublisher {
    
    private final ApplicationEventPublisher publisher;

    public SpringSalaEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(SalaEvent event) {
        publisher.publishEvent(event);
    }
} 