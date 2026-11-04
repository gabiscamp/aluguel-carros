package com.aluguelcarros.controller;

import com.aluguelcarros.dto.AutomovelDTO;
import com.aluguelcarros.service.AutomovelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automoveis")
public class AutomovelController {

    private final AutomovelService automovelService;

    public AutomovelController(AutomovelService automovelService) {
        this.automovelService = automovelService;
    }

    @GetMapping
    public ResponseEntity<List<AutomovelDTO>> listarTodos() {
        return ResponseEntity.ok(automovelService.listarTodos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<AutomovelDTO>> listarDisponiveis() {
        return ResponseEntity.ok(automovelService.listarDisponiveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutomovelDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(automovelService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AutomovelDTO> criar(@Valid @RequestBody AutomovelDTO dto) {
        return ResponseEntity.ok(automovelService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutomovelDTO> atualizar(@PathVariable Long id, @RequestBody AutomovelDTO dto) {
        return ResponseEntity.ok(automovelService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        automovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
