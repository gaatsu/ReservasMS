package com.reservasala.reserva.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHora;
    private Long salaId;
    private Long usuarioId;

    public Reserva(Long id, Long salaId, Long usuarioId, LocalDateTime dataHora) {
        this.id = id;
        this.salaId = salaId;
        this.usuarioId = usuarioId;
        this.dataHora = dataHora;
    }
} 