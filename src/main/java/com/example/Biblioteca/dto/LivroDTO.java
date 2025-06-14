package com.example.Biblioteca.dto;

import com.example.Biblioteca.Enum.GeneroLiterario;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anoPublicacao;
    private Long autorPrincipalId;
    private Set<Long> coautoresIds;
    private GeneroLiterario genero;
    private Set<String> tags;
}