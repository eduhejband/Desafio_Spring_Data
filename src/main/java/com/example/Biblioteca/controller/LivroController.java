// LivroController.java
package com.example.Biblioteca.controller;

import com.example.Biblioteca.Enum.GeneroLiterario;
import com.example.Biblioteca.dto.LivroDTO;
import com.example.Biblioteca.dto.LivroDetalhesDTO;
import com.example.Biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<LivroDetalhesDTO> buscarDetalhesPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarDetalhesPorId(id));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<LivroDTO>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(livroService.buscarPorGenero(GeneroLiterario.valueOf(genero.toUpperCase())));
    }

    @PostMapping
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO livroCriado = livroService.criar(livroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok(livroService.atualizar(id, livroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}