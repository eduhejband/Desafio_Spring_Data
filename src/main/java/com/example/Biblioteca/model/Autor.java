// Autor.java
package com.example.Biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "autor")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "autorPrincipal")
    @Builder.Default
    @ToString.Exclude
    private List<Livro> livrosComoPrincipal = new ArrayList<>();

    @ManyToMany(mappedBy = "coautores")
    @Builder.Default
    @ToString.Exclude
    private List<Livro> livrosComoCoautor = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "logradouro", column = @Column(name = "logradouro")),
            @AttributeOverride(name = "cidade", column = @Column(name = "cidade")),
            @AttributeOverride(name = "pais", column = @Column(name = "pais"))
    })
    private Endereco endereco;
}