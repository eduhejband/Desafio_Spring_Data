package com.example.Biblioteca.dto;

import com.example.Biblioteca.model.Endereco;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDTO {
    private Long id;
    private String nome;
    private Endereco endereco;
    private List<Long> livrosComoPrincipalIds;
    private List<Long> livrosComoCoautorIds;
}