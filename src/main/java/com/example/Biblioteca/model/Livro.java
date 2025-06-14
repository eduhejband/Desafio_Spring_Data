// Livro.java
package com.example.Biblioteca.model;

import com.example.Biblioteca.Enum.GeneroLiterario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String isbn;
    private Integer anoPublicacao;

    @ManyToOne
    @JoinColumn(name = "autor_principal_id")
    private Autor autorPrincipal;

    @ManyToMany
    @JoinTable(
            name = "livro_coautor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    @Builder.Default
    @ToString.Exclude
    private Set<Autor> coautores = new HashSet<>();

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private List<Exemplar> exemplares = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "livro_tags", joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "tag")
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private GeneroLiterario genero;
}