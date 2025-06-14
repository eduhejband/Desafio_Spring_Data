// ExemplarController.java
package com.example.Biblioteca.controller;

import com.example.Biblioteca.dto.ExemplarDTO;
import com.example.Biblioteca.service.ExemplarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exemplares")
public class ExemplarController {
    private final ExemplarService exemplarService;

    public ExemplarController(ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }

    @GetMapping
    public ResponseEntity<List<ExemplarDTO>> listarTodos() {
        return ResponseEntity.ok(exemplarService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExemplarDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(exemplarService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ExemplarDTO> criar(@Valid @RequestBody ExemplarDTO exemplarDTO) {
        ExemplarDTO exemplarCriado = exemplarService.criar(exemplarDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(exemplarCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExemplarDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ExemplarDTO exemplarDTO) {
        return ResponseEntity.ok(exemplarService.atualizar(id, exemplarDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        exemplarService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}