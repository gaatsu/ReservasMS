package com.reservasala.reserva.application.service;

import com.reservasala.reserva.domain.model.Reserva;
import com.reservasala.reserva.domain.repository.ReservaRepository;
import com.reservasala.reserva.infrastructure.messaging.KafkaReservaEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final KafkaReservaEventPublisher eventPublisher;

    public ReservaService(ReservaRepository reservaRepository, KafkaReservaEventPublisher eventPublisher) {
        this.reservaRepository = reservaRepository;
        this.eventPublisher = eventPublisher;
    }

    public Reserva criarReserva(Long salaId, Long usuarioId, LocalDateTime dataHora) {
        // Verificar se já existe reserva para a sala no mesmo horário
        List<Reserva> reservasExistentes = reservaRepository.findBySalaIdAndDataHoraBetween(
            salaId, dataHora, dataHora.plusHours(1));
        
        if (!reservasExistentes.isEmpty()) {
            throw new RuntimeException("Já existe uma reserva para esta sala neste horário");
        }

        Reserva reserva = new Reserva();
        reserva.setSalaId(salaId);
        reserva.setUsuarioId(usuarioId);
        reserva.setDataHora(dataHora);
        
        reserva = reservaRepository.save(reserva);
        eventPublisher.publicarReservaCriada(reserva);
        
        return reserva;
    }

    public void cancelarReserva(Long id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reservaRepository.delete(reserva);
            eventPublisher.publicarReservaDeletada(reserva);
        }
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarReserva(Long id) {
        return reservaRepository.findById(id);
    }
} 