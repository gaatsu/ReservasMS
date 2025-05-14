package com.reservasala.reserva.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaJpaRepository extends JpaRepository<ReservaJpaEntity, Long> {
    List<ReservaJpaEntity> findBySalaIdAndDataHoraBetween(Long salaId, LocalDateTime inicio, LocalDateTime fim);
} 