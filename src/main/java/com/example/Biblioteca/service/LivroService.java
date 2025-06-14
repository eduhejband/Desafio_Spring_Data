package com.example.Biblioteca.service;

import com.example.Biblioteca.dto.LivroDTO;
import com.example.Biblioteca.dto.LivroDetalhesDTO;
import com.example.Biblioteca.Enum.GeneroLiterario;
import com.example.Biblioteca.model.Autor;
import com.example.Biblioteca.model.Livro;
import com.example.Biblioteca.repository.AutorRepository;
import com.example.Biblioteca.repository.LivroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return toDTO(livro);
    }

    public LivroDetalhesDTO buscarDetalhesPorId(Long id) {
        Optional<Object[]> result = livroRepository.findLivroComStatusDisponibilidade(id);
        if (result.isEmpty()) {
            throw new RuntimeException("Livro não encontrado");
        }

        Object[] row = result.get();
        Livro livro = (Livro) row[0];
        String disponibilidade = (String) row[1];

        return toDetalhesDTO(livro, disponibilidade.equals("DISPONIVEL"));
    }

    public List<LivroDTO> buscarPorGenero(GeneroLiterario genero) {
        return livroRepository.findByGeneroWithAutores(genero).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LivroDTO criar(LivroDTO livroDTO) {
        Autor autorPrincipal = autorRepository.findById(livroDTO.getAutorPrincipalId())
                .orElseThrow(() -> new RuntimeException("Autor principal não encontrado"));

        Set<Autor> coautores = new HashSet<>();
        for (Long coautorId : livroDTO.getCoautoresIds()) {
            Autor coautor = autorRepository.findById(coautorId)
                    .orElseThrow(() -> new RuntimeException("Coautor não encontrado: " + coautorId));
            coautores.add(coautor);
        }

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setAutorPrincipal(autorPrincipal);
        livro.setCoautores(coautores);
        livro.setGenero(livroDTO.getGenero());
        livro.setTags(livroDTO.getTags());

        Livro livroSalvo = livroRepository.save(livro);
        return toDTO(livroSalvo);
    }

    @Transactional
    public LivroDTO atualizar(Long id, LivroDTO livroDTO) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Autor autorPrincipal = autorRepository.findById(livroDTO.getAutorPrincipalId())
                .orElseThrow(() -> new RuntimeException("Autor principal não encontrado"));

        Set<Autor> coautores = new HashSet<>();
        for (Long coautorId : livroDTO.getCoautoresIds()) {
            Autor coautor = autorRepository.findById(coautorId)
                    .orElseThrow(() -> new RuntimeException("Coautor não encontrado: " + coautorId));
            coautores.add(coautor);
        }

        livro.setTitulo(livroDTO.getTitulo());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setAutorPrincipal(autorPrincipal);
        livro.setCoautores(coautores);
        livro.setGenero(livroDTO.getGenero());
        livro.setTags(livroDTO.getTags());

        return toDTO(livroRepository.save(livro));
    }

    @Transactional
    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    private LivroDTO toDTO(Livro livro) {
        return LivroDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .isbn(livro.getIsbn())
                .anoPublicacao(livro.getAnoPublicacao())
                .autorPrincipalId(livro.getAutorPrincipal().getId())
                .coautoresIds(livro.getCoautores().stream()
                        .map(a -> a.getId())
                        .collect(Collectors.toSet()))
                .genero(livro.getGenero())
                .tags(livro.getTags())
                .build();
    }

    private LivroDetalhesDTO toDetalhesDTO(Livro livro, boolean disponivel) {
        return LivroDetalhesDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .isbn(livro.getIsbn())
                .anoPublicacao(livro.getAnoPublicacao())
                .autorPrincipalNome(livro.getAutorPrincipal().getNome())
                .coautoresNomes(livro.getCoautores().stream()
                        .map(Autor::getNome)
                        .collect(Collectors.toSet()))
                .genero(livro.getGenero())
                .tags(livro.getTags())
                .disponivel(disponivel)
                .quantidadeExemplares(livro.getExemplares().size())
                .build();
    }
}