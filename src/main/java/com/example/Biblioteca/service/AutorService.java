package com.example.Biblioteca.service;

import com.example.Biblioteca.dto.AutorDTO;
import com.example.Biblioteca.model.Autor;
import com.example.Biblioteca.model.Endereco;
import com.example.Biblioteca.model.Livro;
import com.example.Biblioteca.repository.AutorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;

    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        return toDTO(autor);
    }

    @Transactional
    public AutorDTO criar(AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setNome(autorDTO.getNome());
        autor.setEndereco(autorDTO.getEndereco());
        Autor autorSalvo = autorRepository.save(autor);
        return toDTO(autorSalvo);
    }

    @Transactional
    public AutorDTO atualizar(Long id, AutorDTO autorDTO) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        autor.setNome(autorDTO.getNome());
        autor.setEndereco(autorDTO.getEndereco());
        return toDTO(autorRepository.save(autor));
    }

    @Transactional
    public void deletar(Long id) {
        autorRepository.deleteById(id);
    }

    private AutorDTO toDTO(Autor autor) {
        return AutorDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .endereco(autor.getEndereco())
                .livrosComoPrincipalIds(autor.getLivrosComoPrincipal().stream()
                        .map(Livro::getId)
                        .collect(Collectors.toList()))
                .livrosComoCoautorIds(autor.getLivrosComoCoautor().stream()
                        .map(Livro::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
