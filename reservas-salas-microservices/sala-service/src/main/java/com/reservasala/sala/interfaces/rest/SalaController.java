package com.reservasala.sala.interfaces.rest;

import com.reservasala.sala.application.service.SalaApplicationService;
import com.reservasala.sala.domain.model.Sala;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaApplicationService salaApplicationService;

    public SalaController(SalaApplicationService salaApplicationService) {
        this.salaApplicationService = salaApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<Sala>> listarSalas() {
        return ResponseEntity.ok(salaApplicationService.listarSalas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarSala(@PathVariable Long id) {
        return salaApplicationService.buscarSala(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sala> criarSala(@RequestBody Sala sala) {
        return ResponseEntity.ok(salaApplicationService.criarSala(sala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarSala(@PathVariable Long id, @RequestBody Sala sala) {
        sala.setId(id);
        salaApplicationService.atualizarSala(sala);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id) {
        salaApplicationService.deletarSala(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<Sala>> listarSalasAtivas() {
        return ResponseEntity.ok(salaApplicationService.listarSalasAtivas());
    }
} 