package com.reservasala.sala.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaJpaRepository extends JpaRepository<SalaJpaEntity, Long> {
    List<SalaJpaEntity> findByAtivaTrue();
} 