package com.reservasala.reserva.domain.repository;

import com.reservasala.reserva.domain.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository {
    List<Reserva> findAll();
    List<Reserva> findAll(Sort sort);
    Page<Reserva> findAll(Pageable pageable);
    List<Reserva> findAllById(Iterable<Long> longs);
    long count();
    void deleteById(Long aLong);
    void delete(Reserva entity);
    void deleteAllById(Iterable<? extends Long> longs);
    void deleteAll(Iterable<? extends Reserva> entities);
    void deleteAll();
    <S extends Reserva> S save(S entity);
    <S extends Reserva> List<S> saveAll(Iterable<S> entities);
    Optional<Reserva> findById(Long aLong);
    boolean existsById(Long aLong);
    void flush();
    <S extends Reserva> S saveAndFlush(S entity);
    <S extends Reserva> List<S> saveAllAndFlush(Iterable<S> entities);
    void deleteAllInBatch(Iterable<Reserva> entities);
    void deleteAllByIdInBatch(Iterable<Long> longs);
    void deleteAllInBatch();
    Reserva getOne(Long aLong);
    Reserva getById(Long aLong);
    Reserva getReferenceById(Long aLong);
    List<Reserva> findBySalaIdAndDataHoraBetween(Long salaId, LocalDateTime inicio, LocalDateTime fim);
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findBySalaId(Long salaId);
} 