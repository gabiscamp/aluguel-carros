package com.aluguelcarros.controller;

import com.aluguelcarros.dto.AgenteDTO;
import com.aluguelcarros.model.enums.TipoAgente;
import com.aluguelcarros.service.AgenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agentes")
public class AgenteController {

    private final AgenteService agenteService;

    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @GetMapping
    public ResponseEntity<List<AgenteDTO>> listarTodos() {
        return ResponseEntity.ok(agenteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agenteService.buscarPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AgenteDTO>> listarPorTipo(@PathVariable TipoAgente tipo) {
        return ResponseEntity.ok(agenteService.listarPorTipo(tipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenteDTO> atualizar(@PathVariable Long id, @RequestBody AgenteDTO dto) {
        return ResponseEntity.ok(agenteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agenteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
