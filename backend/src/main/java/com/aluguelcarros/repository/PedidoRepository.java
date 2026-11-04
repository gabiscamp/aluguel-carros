package com.aluguelcarros.repository;

import com.aluguelcarros.model.Pedido;
import com.aluguelcarros.model.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByAgenteId(Long agenteId);
    List<Pedido> findByAutomovelId(Long automovelId);
}
