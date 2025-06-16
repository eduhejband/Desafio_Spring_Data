package com.example.Biblioteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}
	@Bean
	CommandLineRunner init(PasswordEncoder encoder) {
		return args -> {
			System.out.println("HASH 123456 => " + encoder.encode("123456"));
		};
	}


}
