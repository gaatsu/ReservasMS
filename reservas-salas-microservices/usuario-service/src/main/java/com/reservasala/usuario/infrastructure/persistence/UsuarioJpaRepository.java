package com.reservasala.usuario.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, Long> {
    List<UsuarioJpaEntity> findByAtivoTrue();
    Optional<UsuarioJpaEntity> findByMatricula(String matricula);
} 