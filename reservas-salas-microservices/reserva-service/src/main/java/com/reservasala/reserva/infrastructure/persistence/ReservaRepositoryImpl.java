package com.reservasala.reserva.infrastructure.persistence;

import com.reservasala.reserva.domain.model.Reserva;
import com.reservasala.reserva.domain.repository.ReservaRepository;
import com.reservasala.reserva.infrastructure.persistence.entity.ReservaJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@Transactional
public class ReservaRepositoryImpl implements ReservaRepository {

    private final ReservaJpaRepository jpaRepository;

    public ReservaRepositoryImpl(ReservaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Reserva> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> findAll(Sort sort) {
        return jpaRepository.findAll(sort).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Reserva> findAll(Pageable pageable) {
        var page = jpaRepository.findAll(pageable);
        return new PageImpl<>(
                page.getContent().stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public List<Reserva> findAllById(Iterable<Long> longs) {
        return jpaRepository.findAllById(longs).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        jpaRepository.deleteById(aLong);
    }

    @Override
    public void delete(Reserva entity) {
        jpaRepository.delete(toEntity(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        jpaRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Reserva> entities) {
        jpaRepository.deleteAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    @Override
    public <S extends Reserva> S save(S entity) {
        var savedEntity = jpaRepository.save(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends Reserva> List<S> saveAll(Iterable<S> entities) {
        var savedEntities = jpaRepository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
        return savedEntities.stream()
                .map(this::toDomain)
                .map(entity -> (S) entity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Reserva> findById(Long aLong) {
        return jpaRepository.findById(aLong)
                .map(this::toDomain);
    }

    @Override
    public boolean existsById(Long aLong) {
        return jpaRepository.existsById(aLong);
    }

    @Override
    public void flush() {
        jpaRepository.flush();
    }

    @Override
    public <S extends Reserva> S saveAndFlush(S entity) {
        var savedEntity = jpaRepository.saveAndFlush(toEntity(entity));
        return (S) toDomain(savedEntity);
    }

    @Override
    public <S extends Reserva> List<S> saveAllAndFlush(Iterable<S> entities) {
        var savedEntities = jpaRepository.saveAllAndFlush(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
        return savedEntities.stream()
                .map(this::toDomain)
                .map(entity -> (S) entity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllInBatch(Iterable<Reserva> entities) {
        jpaRepository.deleteAllInBatch(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toEntity)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        jpaRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        jpaRepository.deleteAllInBatch();
    }

    @Override
    public Reserva getOne(Long aLong) {
        return toDomain(jpaRepository.getOne(aLong));
    }

    @Override
    public Reserva getById(Long aLong) {
        return toDomain(jpaRepository.getById(aLong));
    }

    @Override
    public Reserva getReferenceById(Long aLong) {
        return toDomain(jpaRepository.getReferenceById(aLong));
    }

    @Override
    public List<Reserva> findBySalaIdAndDataHoraBetween(Long salaId, LocalDateTime inicio, LocalDateTime fim) {
        return jpaRepository.findBySalaIdAndDataHoraBetween(salaId, inicio, fim).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> findBySalaId(Long salaId) {
        return jpaRepository.findBySalaId(salaId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private ReservaJpaEntity toEntity(Reserva reserva) {
        var entity = new ReservaJpaEntity();
        entity.setId(reserva.getId());
        entity.setSalaId(reserva.getSalaId());
        entity.setUsuarioId(reserva.getUsuarioId());
        entity.setDataHora(reserva.getDataHora());
        return entity;
    }

    private Reserva toDomain(ReservaJpaEntity entity) {
        return new Reserva(
                entity.getId(),
                entity.getSalaId(),
                entity.getUsuarioId(),
                entity.getDataHora()
        );
    }
} 