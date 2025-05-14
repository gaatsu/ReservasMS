package com.reservasala.usuario.interfaces.rest;

import com.reservasala.usuario.application.service.UsuarioApplicationService;
import com.reservasala.usuario.domain.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioApplicationService usuarioApplicationService;

    public UsuarioController(UsuarioApplicationService usuarioApplicationService) {
        this.usuarioApplicationService = usuarioApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioApplicationService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        return usuarioApplicationService.buscarUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioApplicationService.criarUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioApplicationService.atualizarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioApplicationService.deletarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Usuario>> listarUsuariosAtivos() {
        return ResponseEntity.ok(usuarioApplicationService.listarUsuariosAtivos());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Usuario> buscarUsuarioPorMatricula(@PathVariable String matricula) {
        return usuarioApplicationService.buscarUsuarioPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 