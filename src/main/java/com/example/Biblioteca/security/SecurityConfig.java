package com.example.Biblioteca.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll()  // Permite todas as requisições sem autenticação

                        // Configuração OAuth 2.0 comentada - descomente e ajuste conforme necessário
                        /*
                        .requestMatchers("/public/**").permitAll()  // Rotas públicas
                        .anyRequest().authenticated()  // Todas as outras rotas requerem autenticação
                        */
                );
        // Configuração OAuth 2.0 comentada - descomente e ajuste conforme necessário
                /*
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                        )
                );
                */
        return http.build();
    }

    // Método para configuração do JWT Decoder - descomente se for usar OAuth 2.0
    /*
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://seu-auth-server/.well-known/jwks.json").build();
    }
    */
}