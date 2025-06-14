// Endereco.java
package com.example.Biblioteca.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {
    private String logradouro;
    private String cidade;
    private String pais;
}