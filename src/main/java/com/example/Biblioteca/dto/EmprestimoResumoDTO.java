package com.example.Biblioteca.dto;

import com.example.Biblioteca.Enum.StatusEmprestimo;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmprestimoResumoDTO {
    private Long id;
    private String usuarioNome;
    private String livroTitulo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private StatusEmprestimo status;
}