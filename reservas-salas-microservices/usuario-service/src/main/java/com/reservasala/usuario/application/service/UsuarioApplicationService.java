package com.reservasala.usuario.application.service;

import com.reservasala.usuario.domain.model.Usuario;
import com.reservasala.usuario.domain.service.UsuarioDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioApplicationService {

    private final UsuarioDomainService usuarioDomainService;

    public UsuarioApplicationService(UsuarioDomainService usuarioDomainService) {
        this.usuarioDomainService = usuarioDomainService;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDomainService.listarUsuarios();
    }

    public Optional<Usuario> buscarUsuario(Long id) {
        return usuarioDomainService.buscarUsuario(id);
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioDomainService.criarUsuario(usuario);
    }

    public void atualizarUsuario(Usuario usuario) {
        usuarioDomainService.atualizarUsuario(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioDomainService.deletarUsuario(id);
    }

    public List<Usuario> listarUsuariosAtivos() {
        return usuarioDomainService.listarUsuariosAtivos();
    }

    public Optional<Usuario> buscarUsuarioPorMatricula(String matricula) {
        return usuarioDomainService.buscarUsuarioPorMatricula(matricula);
    }
} 