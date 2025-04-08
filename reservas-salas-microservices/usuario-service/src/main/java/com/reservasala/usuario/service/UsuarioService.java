package com.reservasala.usuario.service;

import com.reservasala.usuario.model.Usuario;
import com.reservasala.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private KafkaTemplate<String, Usuario> kafkaTemplate;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setDataCadastro(LocalDate.now());
        Usuario usuarioSalvo = repository.save(usuario);
        kafkaTemplate.send("usuario-criado", usuarioSalvo);
        return usuarioSalvo;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
} 