// Exemplar.java
package com.example.Biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoIdentificacao;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @OneToMany(mappedBy = "exemplar")
    @Builder.Default
    @ToString.Exclude
    private List<Emprestimo> historicoEmprestimos = new ArrayList<>();
}