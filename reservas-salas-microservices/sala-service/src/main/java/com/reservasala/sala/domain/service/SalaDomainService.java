package com.reservasala.sala.domain.service;

import com.reservasala.sala.domain.event.SalaEventPublisher;
import com.reservasala.sala.domain.event.SalaAtualizadaEvent;
import com.reservasala.sala.domain.event.SalaCriadaEvent;
import com.reservasala.sala.domain.event.SalaDeletadaEvent;
import com.reservasala.sala.domain.model.Sala;
import com.reservasala.sala.domain.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalaDomainService {

    private final SalaRepository salaRepository;
    private final SalaEventPublisher eventPublisher;

    public SalaDomainService(SalaRepository salaRepository, SalaEventPublisher eventPublisher) {
        this.salaRepository = salaRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> buscarSala(Long id) {
        return salaRepository.findById(id);
    }

    public Sala criarSala(Sala sala) {
        Sala salaSalva = salaRepository.save(sala);
        eventPublisher.publish(new SalaCriadaEvent(salaSalva));
        return salaSalva;
    }

    public void atualizarSala(Sala sala) {
        Sala salaAtualizada = salaRepository.save(sala);
        eventPublisher.publish(new SalaAtualizadaEvent(salaAtualizada));
    }

    public void deletarSala(Long id) {
        salaRepository.findById(id).ifPresent(sala -> {
            salaRepository.deleteById(id);
            eventPublisher.publish(new SalaDeletadaEvent(sala));
        });
    }

    public List<Sala> listarSalasAtivas() {
        return salaRepository.findByAtivaTrue();
    }
} 