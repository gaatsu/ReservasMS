package com.reservasala.sala.service;

import com.reservasala.sala.model.Sala;
import com.reservasala.sala.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    @Autowired
    private SalaRepository repository;

    @Autowired
    private KafkaTemplate<String, Sala> kafkaTemplate;

    public List<Sala> listar() {
        return repository.findAll();
    }

    public Sala salvar(Sala sala) {
        Sala salaSalva = repository.save(sala);
        kafkaTemplate.send("sala-criada", salaSalva);
        return salaSalva;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
} 