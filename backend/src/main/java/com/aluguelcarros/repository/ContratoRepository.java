package com.aluguelcarros.repository;

import com.aluguelcarros.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    Optional<Contrato> findByPedidoId(Long pedidoId);
    List<Contrato> findByAtivoTrue();
}
