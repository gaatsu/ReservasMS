package com.reservasala.reserva.domain.service;

import com.reservasala.reserva.domain.event.ReservaEventPublisher;
import com.reservasala.reserva.domain.event.ReservaAtualizadaEvent;
import com.reservasala.reserva.domain.event.ReservaCriadaEvent;
import com.reservasala.reserva.domain.event.ReservaDeletadaEvent;
import com.reservasala.reserva.domain.model.Reserva;
import com.reservasala.reserva.domain.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservaDomainService {

    private final ReservaRepository reservaRepository;
    private final ReservaEventPublisher eventPublisher;

    public ReservaDomainService(ReservaRepository reservaRepository, ReservaEventPublisher eventPublisher) {
        this.reservaRepository = reservaRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarReserva(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva criarReserva(Reserva reserva) {
        Reserva reservaSalva = reservaRepository.save(reserva);
        eventPublisher.publish(new ReservaCriadaEvent(reservaSalva));
        return reservaSalva;
    }

    public void atualizarReserva(Reserva reserva) {
        Reserva reservaAtualizada = reservaRepository.save(reserva);
        eventPublisher.publish(new ReservaAtualizadaEvent(reservaAtualizada));
    }

    public void deletarReserva(Long id) {
        reservaRepository.findById(id).ifPresent(reserva -> {
            reservaRepository.deleteById(id);
            eventPublisher.publish(new ReservaDeletadaEvent(reserva));
        });
    }

    public List<Reserva> listarReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> listarReservasPorSala(Long salaId) {
        return reservaRepository.findBySalaId(salaId);
    }
} 