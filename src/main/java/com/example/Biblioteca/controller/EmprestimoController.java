// EmprestimoController.java
package com.example.Biblioteca.controller;

import com.example.Biblioteca.dto.EmprestimoDTO;
import com.example.Biblioteca.dto.EmprestimoResumoDTO;
import com.example.Biblioteca.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResumoDTO>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodosResumidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EmprestimoResumoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(emprestimoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<EmprestimoDTO> criar(@Valid @RequestBody EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO emprestimoCriado = emprestimoService.criar(emprestimoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoCriado);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoDTO> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.devolver(id));
    }

    @PutMapping("/atualizar-status")
    public ResponseEntity<Void> atualizarStatusAtrasados() {
        emprestimoService.atualizarStatusAtrasados();
        return ResponseEntity.noContent().build();
    }
}