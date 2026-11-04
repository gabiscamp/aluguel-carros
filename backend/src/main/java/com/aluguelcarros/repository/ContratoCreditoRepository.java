package com.aluguelcarros.repository;

import com.aluguelcarros.model.ContratoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContratoCreditoRepository extends JpaRepository<ContratoCredito, Long> {
    Optional<ContratoCredito> findByContratoId(Long contratoId);
}
