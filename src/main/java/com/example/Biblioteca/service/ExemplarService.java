package com.example.Biblioteca.service;

import com.example.Biblioteca.dto.ExemplarDTO;
import com.example.Biblioteca.model.Exemplar;
import com.example.Biblioteca.model.Livro;
import com.example.Biblioteca.repository.ExemplarRepository;
import com.example.Biblioteca.repository.LivroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExemplarService {
    private final ExemplarRepository exemplarRepository;
    private final LivroRepository livroRepository;

    public List<ExemplarDTO> listarTodos() {
        return exemplarRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExemplarDTO buscarPorId(Long id) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));
        return toDTO(exemplar);
    }

    @Transactional
    public ExemplarDTO criar(ExemplarDTO exemplarDTO) {
        Livro livro = livroRepository.findById(exemplarDTO.getLivroId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Exemplar exemplar = new Exemplar();
        exemplar.setCodigoIdentificacao(exemplarDTO.getCodigoIdentificacao());
        exemplar.setLivro(livro);

        Exemplar exemplarSalvo = exemplarRepository.save(exemplar);
        return toDTO(exemplarSalvo);
    }

    @Transactional
    public ExemplarDTO atualizar(Long id, ExemplarDTO exemplarDTO) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));
        exemplar.setCodigoIdentificacao(exemplarDTO.getCodigoIdentificacao());
        return toDTO(exemplarRepository.save(exemplar));
    }

    @Transactional
    public void deletar(Long id) {
        exemplarRepository.deleteById(id);
    }

    private ExemplarDTO toDTO(Exemplar exemplar) {
        return ExemplarDTO.builder()
                .id(exemplar.getId())
                .codigoIdentificacao(exemplar.getCodigoIdentificacao())
                .livroId(exemplar.getLivro().getId())
                .historicoEmprestimosIds(exemplar.getHistoricoEmprestimos().stream()
                        .map(e -> e.getId())
                        .collect(Collectors.toList()))
                .build();
    }
}
