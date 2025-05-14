package com.reservasala.sala.domain.repository;

import com.reservasala.sala.domain.model.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository {
    List<Sala> findAll();
    List<Sala> findAll(Sort sort);
    Page<Sala> findAll(Pageable pageable);
    List<Sala> findAllById(Iterable<Long> longs);
    long count();
    void deleteById(Long aLong);
    void delete(Sala entity);
    void deleteAllById(Iterable<? extends Long> longs);
    void deleteAll(Iterable<? extends Sala> entities);
    void deleteAll();
    <S extends Sala> S save(S entity);
    <S extends Sala> List<S> saveAll(Iterable<S> entities);
    Optional<Sala> findById(Long aLong);
    boolean existsById(Long aLong);
    void flush();
    <S extends Sala> S saveAndFlush(S entity);
    <S extends Sala> List<S> saveAllAndFlush(Iterable<S> entities);
    void deleteAllInBatch(Iterable<Sala> entities);
    void deleteAllByIdInBatch(Iterable<Long> longs);
    void deleteAllInBatch();
    Sala getOne(Long aLong);
    Sala getById(Long aLong);
    Sala getReferenceById(Long aLong);
    List<Sala> findByAtivaTrue();
} 