package com.aluguelcarros.controller;

import com.aluguelcarros.dto.ClienteDTO;
import com.aluguelcarros.model.Usuario;
import com.aluguelcarros.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @GetMapping("/me")
    public ResponseEntity<ClienteDTO> meuPerfil(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(clienteService.buscarPorId(usuario.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    @PostMapping("/{id}/empregadores")
    public ResponseEntity<ClienteDTO> adicionarEmpregador(@PathVariable Long id,
                                                           @RequestBody ClienteDTO.EmpregadorDTO empregadorDTO) {
        return ResponseEntity.ok(clienteService.adicionarEmpregador(id, empregadorDTO));
    }

    @DeleteMapping("/{clienteId}/empregadores/{empregadorId}")
    public ResponseEntity<Void> removerEmpregador(@PathVariable Long clienteId,
                                                   @PathVariable Long empregadorId) {
        clienteService.removerEmpregador(clienteId, empregadorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
