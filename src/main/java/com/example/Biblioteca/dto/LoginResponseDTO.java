package com.example.Biblioteca.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}