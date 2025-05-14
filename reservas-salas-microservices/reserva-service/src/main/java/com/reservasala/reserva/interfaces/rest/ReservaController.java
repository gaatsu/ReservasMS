package com.reservasala.reserva.interfaces.rest;

import com.reservasala.reserva.application.service.ReservaService;
import com.reservasala.reserva.domain.model.Reserva;
import com.reservasala.reserva.interfaces.rest.dto.ReservaRequest;
import com.reservasala.reserva.interfaces.rest.dto.ReservaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService reservaService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarReservas() {
        List<Reserva> reservas = reservaService.listarReservas();
        List<ReservaResponse> response = reservas.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> buscarReserva(@PathVariable Long id) {
        return reservaService.buscarReserva(id)
            .map(this::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> criarReserva(@RequestBody ReservaRequest request) {
        LocalDateTime dataHora = LocalDateTime.parse(request.getDataHora(), formatter);
        Reserva reserva = reservaService.criarReserva(
            request.getSalaId(),
            request.getUsuarioId(),
            dataHora
        );
        return ResponseEntity.ok(toResponse(reserva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    private ReservaResponse toResponse(Reserva reserva) {
        return new ReservaResponse(
            reserva.getId(),
            reserva.getSalaId(),
            reserva.getUsuarioId(),
            reserva.getDataHora().format(formatter)
        );
    }
} 