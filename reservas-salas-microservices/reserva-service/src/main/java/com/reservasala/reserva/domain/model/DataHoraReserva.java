package com.reservasala.reserva.domain.model;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class DataHoraReserva {
    LocalDateTime value;

    public DataHoraReserva(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("Data e hora da reserva não podem ser nulas");
        }
        if (value.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data e hora da reserva não podem ser no passado");
        }
        this.value = value;
    }

    public boolean conflitaCom(DataHoraReserva outra) {
        return this.value.isEqual(outra.value) || 
               (this.value.isBefore(outra.value.plusHours(1)) && 
                this.value.plusHours(1).isAfter(outra.value));
    }
} 