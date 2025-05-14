package com.reservasala.usuario.domain.repository;

import com.reservasala.usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository {
    List<Usuario> findAll();
    List<Usuario> findAll(Sort sort);
    Page<Usuario> findAll(Pageable pageable);
    List<Usuario> findAllById(Iterable<Long> longs);
    long count();
    void deleteById(Long aLong);
    void delete(Usuario entity);
    void deleteAllById(Iterable<? extends Long> longs);
    void deleteAll(Iterable<? extends Usuario> entities);
    void deleteAll();
    <S extends Usuario> S save(S entity);
    <S extends Usuario> List<S> saveAll(Iterable<S> entities);
    Optional<Usuario> findById(Long aLong);
    boolean existsById(Long aLong);
    void flush();
    <S extends Usuario> S saveAndFlush(S entity);
    <S extends Usuario> List<S> saveAllAndFlush(Iterable<S> entities);
    void deleteAllInBatch(Iterable<Usuario> entities);
    void deleteAllByIdInBatch(Iterable<Long> longs);
    void deleteAllInBatch();
    Usuario getOne(Long aLong);
    Usuario getById(Long aLong);
    Usuario getReferenceById(Long aLong);
    List<Usuario> findByAtivoTrue();
    Optional<Usuario> findByMatricula(String matricula);
} 