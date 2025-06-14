package com.example.Biblioteca.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExemplarDTO {
    private Long id;
    private String codigoIdentificacao;
    private Long livroId;
    private List<Long> historicoEmprestimosIds;
}