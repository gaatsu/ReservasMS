package com.reservasala.sala.application.service;

import com.reservasala.sala.domain.model.Sala;
import com.reservasala.sala.domain.service.SalaDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalaApplicationService {

    private final SalaDomainService salaDomainService;

    public SalaApplicationService(SalaDomainService salaDomainService) {
        this.salaDomainService = salaDomainService;
    }

    public List<Sala> listarSalas() {
        return salaDomainService.listarSalas();
    }

    public Optional<Sala> buscarSala(Long id) {
        return salaDomainService.buscarSala(id);
    }

    public Sala criarSala(Sala sala) {
        return salaDomainService.criarSala(sala);
    }

    public void atualizarSala(Sala sala) {
        salaDomainService.atualizarSala(sala);
    }

    public void deletarSala(Long id) {
        salaDomainService.deletarSala(id);
    }

    public List<Sala> listarSalasAtivas() {
        return salaDomainService.listarSalasAtivas();
    }
} 