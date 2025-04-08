package com.reservasala.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String listar(Model model) {
        try {
            // Buscar reservas
            ResponseEntity<Map[]> reservasResponse = restTemplate.getForEntity(
                "http://reserva-service:8083/api/reservas", 
                Map[].class
            );
            List<Map> reservas = Arrays.asList(reservasResponse.getBody());
            logger.info("Reservas encontradas: {}", reservas.size());

            // Buscar salas
            ResponseEntity<Map[]> salasResponse = restTemplate.getForEntity(
                "http://sala-service:8082/api/salas", 
                Map[].class
            );
            List<Map> salas = Arrays.asList(salasResponse.getBody());
            logger.info("Salas encontradas: {}", salas.size());

            // Buscar usuários
            ResponseEntity<Map[]> usuariosResponse = restTemplate.getForEntity(
                "http://usuario-service:8081/api/usuarios", 
                Map[].class
            );
            List<Map> usuarios = Arrays.asList(usuariosResponse.getBody());
            logger.info("Usuários encontrados: {}", usuarios.size());

            // Criar um mapa para fácil acesso aos nomes das salas e usuários
            Map<Long, String> salaNomes = salas.stream()
                .collect(Collectors.toMap(
                    sala -> Long.valueOf(sala.get("id").toString()),
                    sala -> sala.get("nome").toString()
                ));
            
            Map<Long, String> usuarioNomes = usuarios.stream()
                .collect(Collectors.toMap(
                    usuario -> Long.valueOf(usuario.get("id").toString()),
                    usuario -> usuario.get("nome").toString()
                ));
            
            // Adicionar nomes de salas e usuários às reservas e formatar datas
            for (Map reserva : reservas) {
                if (reserva.get("salaId") == null || reserva.get("usuarioId") == null) {
                    logger.error("Reserva com dados faltando: {}", reserva);
                    continue;
                }
                
                Long salaId = Long.valueOf(reserva.get("salaId").toString());
                Long usuarioId = Long.valueOf(reserva.get("usuarioId").toString());
                
                reserva.put("salaNome", salaNomes.getOrDefault(salaId, "Sala não encontrada"));
                reserva.put("usuarioNome", usuarioNomes.getOrDefault(usuarioId, "Usuário não encontrado"));
                
                // Formatar a data para exibição
                if (reserva.get("dataHora") != null) {
                    try {
                        String dataHoraStr = reserva.get("dataHora").toString();
                        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr);
                        reserva.put("dataHoraFormatada", dataHora.format(DATE_FORMATTER));
                    } catch (DateTimeParseException e) {
                        logger.error("Erro ao formatar data: {}", e.getMessage());
                        reserva.put("dataHoraFormatada", reserva.get("dataHora").toString());
                    }
                }
            }
            
            // Marcar salas como ocupadas baseado nas reservas
            for (Map sala : salas) {
                Long salaId = Long.valueOf(sala.get("id").toString());
                boolean ocupada = reservas.stream()
                    .anyMatch(reserva -> {
                        if (reserva.get("salaId") == null) return false;
                        Long reservaSalaId = Long.valueOf(reserva.get("salaId").toString());
                        return reservaSalaId.equals(salaId);
                    });
                sala.put("disponivel", !ocupada);
            }

            model.addAttribute("reservas", reservas);
            model.addAttribute("salas", salas);
            model.addAttribute("usuarios", usuarios);
            
            // Adicionar mensagens de sucesso/erro
            String success = (String) model.getAttribute("success");
            String error = (String) model.getAttribute("error");
            if (success != null) {
                model.addAttribute("message", success);
                model.addAttribute("messageType", "success");
            } else if (error != null) {
                model.addAttribute("message", error);
                model.addAttribute("messageType", "danger");
            }
            
            return "reservas";
        } catch (Exception e) {
            logger.error("Erro ao carregar dados: {}", e.getMessage(), e);
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
            return "reservas";
        }
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam String salaId,
            @RequestParam String usuarioId,
            @RequestParam String dataHora,
            Model model) {
        
        try {
            logger.info("Dados recebidos - SalaId: {}, UsuarioId: {}, DataHora: {}", salaId, usuarioId, dataHora);

            // Criar um objeto simplificado para enviar à API
            Map<String, Object> reserva = new HashMap<>();
            reserva.put("salaId", Long.parseLong(salaId));
            reserva.put("usuarioId", Long.parseLong(usuarioId));
            reserva.put("dataHora", dataHora + ":00");  // Adiciona segundos diretamente

            logger.info("Enviando requisição para criar reserva: {}", reserva);
            
            // Enviar a requisição para o serviço de reservas
            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://reserva-service:8083/api/reservas",
                reserva,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Reserva criada com sucesso");
                model.addAttribute("success", "Reserva criada com sucesso");
            } else {
                logger.error("Erro ao criar reserva. Status: {}", response.getStatusCode());
                model.addAttribute("error", "Erro ao criar reserva");
            }

            return "redirect:/reservas";
        } catch (Exception e) {
            logger.error("Erro ao criar reserva: {}", e.getMessage(), e);
            model.addAttribute("error", "Erro ao criar reserva: " + e.getMessage());
            return "redirect:/reservas";
        }
    }

    @PostMapping("/usuarios")
    public String criarUsuario(
            @RequestParam String nome,
            @RequestParam String email,
            Model model) {
        
        try {
            Map<String, Object> usuario = new HashMap<>();
            usuario.put("nome", nome);
            usuario.put("email", email);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://usuario-service:8081/api/usuarios",
                usuario,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Usuário criado com sucesso");
            } else {
                model.addAttribute("error", "Erro ao criar usuário");
            }

            return "redirect:/reservas";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar usuário: " + e.getMessage());
            return "redirect:/reservas";
        }
    }

    @PostMapping("/salas")
    public String criarSala(
            @RequestParam String nome,
            @RequestParam int capacidade,
            Model model) {
        
        try {
            Map<String, Object> sala = new HashMap<>();
            sala.put("nome", nome);
            sala.put("capacidade", capacidade);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://sala-service:8082/api/salas",
                sala,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("success", "Sala criada com sucesso");
            } else {
                model.addAttribute("error", "Erro ao criar sala");
            }

            return "redirect:/reservas";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar sala: " + e.getMessage());
            return "redirect:/reservas";
        }
    }

    @PostMapping(value = "/salvar", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, String>> salvarJson(@RequestBody Map<String, Object> reservaJson, Model model) {
        try {
            logger.info("Dados JSON recebidos: {}", reservaJson);
            
            // Extrair dados do JSON
            Integer salaIdInt = (Integer) reservaJson.get("salaId");
            Integer usuarioIdInt = (Integer) reservaJson.get("usuarioId");
            String dataHora = (String) reservaJson.get("dataHora");
            
            if (salaIdInt == null || usuarioIdInt == null || dataHora == null) {
                logger.error("Dados inválidos: salaId={}, usuarioId={}, dataHora={}", salaIdInt, usuarioIdInt, dataHora);
                return ResponseEntity.badRequest().body(Map.of("error", "Dados inválidos"));
            }
            
            // Converter para o formato esperado
            Map<String, Object> reserva = new HashMap<>();
            reserva.put("salaId", Long.valueOf(salaIdInt));
            reserva.put("usuarioId", Long.valueOf(usuarioIdInt));
            reserva.put("dataHora", dataHora);
            
            logger.info("Enviando requisição para criar reserva: {}", reserva);
            
            // Enviar a requisição para o serviço de reservas
            ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://reserva-service:8083/api/reservas",
                reserva,
                Map.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Reserva criada com sucesso via JSON");
                return ResponseEntity.ok(Map.of("success", "Reserva criada com sucesso"));
            } else {
                logger.error("Erro ao criar reserva via JSON. Status: {}", response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body(Map.of("error", "Erro ao criar reserva"));
            }
        } catch (Exception e) {
            logger.error("Erro ao criar reserva via JSON: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        try {
            logger.info("Recebendo requisição para deletar reserva com ID: {}", id);
            
            restTemplate.delete("http://reserva-service:8083/api/reservas/" + id);
            
            logger.info("Reserva deletada com sucesso");
            return ResponseEntity.ok(Map.of("message", "Reserva deletada com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao deletar reserva: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao deletar reserva: " + e.getMessage()));
        }
    }

    @DeleteMapping("/salas/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deletarSala(@PathVariable Long id) {
        try {
            logger.info("Recebendo requisição para deletar sala com ID: {}", id);
            
            restTemplate.delete("http://sala-service:8082/api/salas/" + id);
            
            logger.info("Sala deletada com sucesso");
            return ResponseEntity.ok(Map.of("message", "Sala deletada com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao deletar sala: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao deletar sala: " + e.getMessage()));
        }
    }

    @PutMapping("/salas/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> editarSala(
            @PathVariable Long id,
            @RequestBody Map<String, Object> salaJson) {
        try {
            logger.info("Recebendo requisição para editar sala com ID: {}", id);
            
            restTemplate.put("http://sala-service:8082/api/salas/" + id, salaJson);
            
            logger.info("Sala editada com sucesso");
            return ResponseEntity.ok(Map.of("message", "Sala editada com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao editar sala: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao editar sala: " + e.getMessage()));
        }
    }

    @DeleteMapping("/usuarios/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deletarUsuario(@PathVariable Long id) {
        try {
            logger.info("Recebendo requisição para deletar usuário com ID: {}", id);
            
            restTemplate.delete("http://usuario-service:8081/api/usuarios/" + id);
            
            logger.info("Usuário deletado com sucesso");
            return ResponseEntity.ok(Map.of("message", "Usuário deletado com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao deletar usuário: " + e.getMessage()));
        }
    }

    @PutMapping("/usuarios/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> editarUsuario(
            @PathVariable Long id,
            @RequestBody Map<String, Object> usuarioJson) {
        try {
            logger.info("Recebendo requisição para editar usuário com ID: {}", id);
            
            restTemplate.put("http://usuario-service:8081/api/usuarios/" + id, usuarioJson);
            
            logger.info("Usuário editado com sucesso");
            return ResponseEntity.ok(Map.of("message", "Usuário editado com sucesso"));
        } catch (Exception e) {
            logger.error("Erro ao editar usuário: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro ao editar usuário: " + e.getMessage()));
        }
    }
} 