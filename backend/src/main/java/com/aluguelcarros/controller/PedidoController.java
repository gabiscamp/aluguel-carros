package com.aluguelcarros.controller;

import com.aluguelcarros.dto.PedidoDTO;
import com.aluguelcarros.model.Usuario;
import com.aluguelcarros.model.enums.StatusPedido;
import com.aluguelcarros.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO dto,
                                            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(pedidoService.criar(dto, usuario.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> modificar(@PathVariable Long id,
                                                @RequestBody PedidoDTO dto,
                                                @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(pedidoService.modificar(id, dto, usuario.getId()));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoDTO> cancelar(@PathVariable Long id,
                                               @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(pedidoService.cancelar(id, usuario.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> consultar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.consultar(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/meus")
    public ResponseEntity<List<PedidoDTO>> listarMeusPedidos(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(pedidoService.listarPorCliente(usuario.getId()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PedidoDTO>> listarPorStatus(@PathVariable StatusPedido status) {
        return ResponseEntity.ok(pedidoService.listarPorStatus(status));
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<PedidoDTO> avaliar(@PathVariable Long id,
                                              @RequestBody Map<String, String> body,
                                              @AuthenticationPrincipal Usuario usuario) {
        String parecer = body.getOrDefault("parecerFinanceiro", "");
        return ResponseEntity.ok(pedidoService.avaliar(id, usuario.getId(), parecer));
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<PedidoDTO> aprovar(@PathVariable Long id,
                                              @RequestBody(required = false) Map<String, String> body,
                                              @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(pedidoService.aprovar(id, usuario.getId()));
    }

    @PutMapping("/{id}/rejeitar")
    public ResponseEntity<PedidoDTO> rejeitar(@PathVariable Long id,
                                               @RequestBody(required = false) Map<String, String> body,
                                               @AuthenticationPrincipal Usuario usuario) {
        String motivo = body != null ? body.getOrDefault("motivo", "Pedido rejeitado") : "Pedido rejeitado";
        return ResponseEntity.ok(pedidoService.rejeitar(id, usuario.getId(), motivo));
    }
}
