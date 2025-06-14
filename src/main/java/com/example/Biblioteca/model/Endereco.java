// Endereco.java
package com.example.Biblioteca.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@Table(name = "endereco")
@AllArgsConstructor
@Builder
public class Endereco {
    private String logradouro;
    private String cidade;
    private String pais;
}