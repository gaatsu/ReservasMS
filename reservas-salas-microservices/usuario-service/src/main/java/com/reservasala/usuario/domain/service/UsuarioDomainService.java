package com.reservasala.usuario.domain.service;

import com.reservasala.usuario.domain.event.UsuarioEventPublisher;
import com.reservasala.usuario.domain.event.UsuarioAtualizadoEvent;
import com.reservasala.usuario.domain.event.UsuarioCriadoEvent;
import com.reservasala.usuario.domain.event.UsuarioDeletadoEvent;
import com.reservasala.usuario.domain.model.Usuario;
import com.reservasala.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioDomainService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioEventPublisher eventPublisher;

    public UsuarioDomainService(UsuarioRepository usuarioRepository, UsuarioEventPublisher eventPublisher) {
        this.usuarioRepository = usuarioRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario criarUsuario(Usuario usuario) {
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        eventPublisher.publish(new UsuarioCriadoEvent(usuarioSalvo));
        return usuarioSalvo;
    }

    public void atualizarUsuario(Usuario usuario) {
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        eventPublisher.publish(new UsuarioAtualizadoEvent(usuarioAtualizado));
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuarioRepository.deleteById(id);
            eventPublisher.publish(new UsuarioDeletadoEvent(usuario));
        });
    }

    public List<Usuario> listarUsuariosAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }

    public Optional<Usuario> buscarUsuarioPorMatricula(String matricula) {
        return usuarioRepository.findByMatricula(matricula);
    }
} 