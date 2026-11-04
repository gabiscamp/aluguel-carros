package com.aluguelcarros.controller;

import com.aluguelcarros.dto.ContratoDTO;
import com.aluguelcarros.dto.ContratoCreditoDTO;
import com.aluguelcarros.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarTodos() {
        return ResponseEntity.ok(contratoService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ContratoDTO>> listarAtivos() {
        return ResponseEntity.ok(contratoService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<ContratoDTO> buscarPorPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(contratoService.buscarPorPedido(pedidoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> atualizar(@PathVariable Long id, @RequestBody ContratoDTO dto) {
        return ResponseEntity.ok(contratoService.atualizar(id, dto));
    }

    @PostMapping("/{id}/credito")
    public ResponseEntity<ContratoDTO> associarCredito(@PathVariable Long id,
                                                        @Valid @RequestBody ContratoCreditoDTO creditoDTO) {
        return ResponseEntity.ok(contratoService.associarCredito(id, creditoDTO));
    }
}
