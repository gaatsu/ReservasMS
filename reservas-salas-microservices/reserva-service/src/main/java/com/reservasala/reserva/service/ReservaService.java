package com.reservasala.reserva.service;

import com.reservasala.reserva.model.Reserva;
import com.reservasala.reserva.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    @Autowired
    private KafkaTemplate<String, Reserva> kafkaTemplate;

    public List<Reserva> listar() {
        return repository.findAll();
    }

    public Reserva salvar(Reserva reserva) {
        // Verificar se a sala está disponível no horário
        List<Reserva> reservasExistentes = repository.findBySalaIdAndDataHoraBetween(
            reserva.getSalaId(),
            reserva.getDataHora(),
            reserva.getDataHora().plusHours(1)
        );

        if (!reservasExistentes.isEmpty()) {
            throw new RuntimeException("Sala já está reservada neste horário");
        }

        Reserva reservaSalva = repository.save(reserva);
        kafkaTemplate.send("reserva-criada", reservaSalva);
        return reservaSalva;
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada");
        }
        Reserva reserva = repository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        repository.deleteById(id);
        kafkaTemplate.send("reserva-deletada", reserva);
    }
} 