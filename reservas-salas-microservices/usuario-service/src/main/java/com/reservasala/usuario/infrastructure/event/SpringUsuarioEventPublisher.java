package com.reservasala.usuario.infrastructure.event;

import com.reservasala.usuario.domain.event.UsuarioEvent;
import com.reservasala.usuario.domain.event.UsuarioEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringUsuarioEventPublisher implements UsuarioEventPublisher {
    
    private final ApplicationEventPublisher publisher;

    public SpringUsuarioEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(UsuarioEvent event) {
        publisher.publishEvent(event);
    }
} 