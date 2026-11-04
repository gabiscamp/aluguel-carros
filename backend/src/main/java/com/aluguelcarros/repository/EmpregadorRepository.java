package com.aluguelcarros.repository;

import com.aluguelcarros.model.Empregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpregadorRepository extends JpaRepository<Empregador, Long> {
    List<Empregador> findByClienteId(Long clienteId);
    long countByClienteId(Long clienteId);
}
