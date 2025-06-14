// AutorController.java
package com.example.Biblioteca.controller;

import com.example.Biblioteca.dto.AutorDTO;
import com.example.Biblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AutorDTO> criar(@Valid @RequestBody AutorDTO autorDTO) {
        AutorDTO autorCriado = autorService.criar(autorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AutorDTO autorDTO) {
        return ResponseEntity.ok(autorService.atualizar(id, autorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}