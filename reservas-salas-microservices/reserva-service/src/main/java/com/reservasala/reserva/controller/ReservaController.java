package com.reservasala.reserva.controller;

import com.reservasala.reserva.model.Reserva;
import com.reservasala.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
    
    @Autowired
    private ReservaService service;

    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        List<Reserva> reservas = service.listar();
        logger.info("Listando {} reservas", reservas.size());
        return ResponseEntity.ok(reservas);
    }

    @PostMapping
    public ResponseEntity<Reserva> salvar(@RequestBody Map<String, Object> reservaMap) {
        try {
            logger.info("Recebendo requisição para criar reserva: {}", reservaMap);
            
            // Extrair dados do mapa
            Long salaId = null;
            Long usuarioId = null;
            String dataHoraStr = null;
            
            try {
                if (reservaMap.get("salaId") instanceof Integer) {
                    salaId = Long.valueOf((Integer) reservaMap.get("salaId"));
                } else if (reservaMap.get("salaId") instanceof Long) {
                    salaId = (Long) reservaMap.get("salaId");
                } else if (reservaMap.get("salaId") instanceof String) {
                    salaId = Long.parseLong((String) reservaMap.get("salaId"));
                }
                
                if (reservaMap.get("usuarioId") instanceof Integer) {
                    usuarioId = Long.valueOf((Integer) reservaMap.get("usuarioId"));
                } else if (reservaMap.get("usuarioId") instanceof Long) {
                    usuarioId = (Long) reservaMap.get("usuarioId");
                } else if (reservaMap.get("usuarioId") instanceof String) {
                    usuarioId = Long.parseLong((String) reservaMap.get("usuarioId"));
                }
                
                dataHoraStr = (String) reservaMap.get("dataHora");
            } catch (Exception e) {
                logger.error("Erro ao extrair dados da requisição: {}", e.getMessage());
                return ResponseEntity.badRequest().build();
            }
            
            if (salaId == null) {
                logger.error("Sala ID não pode ser nulo");
                return ResponseEntity.badRequest().build();
            }
            if (usuarioId == null) {
                logger.error("Usuário ID não pode ser nulo");
                return ResponseEntity.badRequest().build();
            }
            if (dataHoraStr == null) {
                logger.error("Data/Hora não pode ser nula");
                return ResponseEntity.badRequest().build();
            }
            
            // Converter a string de data para LocalDateTime
            LocalDateTime dataHora;
            try {
                dataHora = LocalDateTime.parse(dataHoraStr);
                logger.info("Data hora convertida: {}", dataHora);
            } catch (Exception e) {
                logger.error("Erro ao converter a data: {}. Formato recebido: {}", e.getMessage(), dataHoraStr);
                return ResponseEntity.badRequest().build();
            }
            
            // Criar objeto Reserva
            Reserva reserva = new Reserva();
            reserva.setSalaId(salaId);
            reserva.setUsuarioId(usuarioId);
            reserva.setDataHora(dataHora);
            
            logger.info("Reserva criada a partir dos dados recebidos: {}", reserva);
            
            // Salvar a reserva
            Reserva reservaSalva = service.salvar(reserva);
            logger.info("Reserva salva com sucesso: {}", reservaSalva);
            
            return ResponseEntity.ok(reservaSalva);
        } catch (RuntimeException e) {
            logger.error("Erro ao criar reserva: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            logger.info("Recebendo requisição para deletar reserva com ID: {}", id);
            service.deletar(id);
            logger.info("Reserva deletada com sucesso");
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error("Erro ao deletar reserva: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
} 