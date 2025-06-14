package com.example.Biblioteca.dto;

import com.example.Biblioteca.Enum.GeneroLiterario;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroDetalhesDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anoPublicacao;
    private String autorPrincipalNome;
    private Set<String> coautoresNomes;
    private GeneroLiterario genero;
    private Set<String> tags;
    private boolean disponivel;
    private int quantidadeExemplares;
}