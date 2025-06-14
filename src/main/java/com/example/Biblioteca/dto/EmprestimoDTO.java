package com.example.Biblioteca.dto;

import com.example.Biblioteca.Enum.StatusEmprestimo;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmprestimoDTO {
    private Long id;
    private Long usuarioId;
    private Long exemplarId;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private StatusEmprestimo status;
}