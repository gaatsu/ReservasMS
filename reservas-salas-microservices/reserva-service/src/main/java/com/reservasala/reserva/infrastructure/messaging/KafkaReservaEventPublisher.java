package com.reservasala.reserva.infrastructure.messaging;

import com.reservasala.reserva.domain.event.ReservaCriadaEvent;
import com.reservasala.reserva.domain.event.ReservaDeletadaEvent;
import com.reservasala.reserva.domain.model.Reserva;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaReservaEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_RESERVA_CRIADA = "reserva-criada";
    private static final String TOPIC_RESERVA_DELETADA = "reserva-deletada";

    public KafkaReservaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarReservaCriada(Reserva reserva) {
        ReservaCriadaEvent event = new ReservaCriadaEvent(reserva);
        kafkaTemplate.send(TOPIC_RESERVA_CRIADA, event);
    }

    public void publicarReservaDeletada(Reserva reserva) {
        ReservaDeletadaEvent event = new ReservaDeletadaEvent(reserva);
        kafkaTemplate.send(TOPIC_RESERVA_DELETADA, event);
    }
} 