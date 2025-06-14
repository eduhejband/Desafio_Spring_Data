package com.example.Biblioteca.service;

import com.example.Biblioteca.dto.EmprestimoDTO;
import com.example.Biblioteca.dto.EmprestimoResumoDTO;
import com.example.Biblioteca.Enum.StatusEmprestimo;
import com.example.Biblioteca.model.Emprestimo;
import com.example.Biblioteca.model.Exemplar;
import com.example.Biblioteca.model.Usuario;
import com.example.Biblioteca.repository.EmprestimoRepository;
import com.example.Biblioteca.repository.ExemplarRepository;
import com.example.Biblioteca.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ExemplarRepository exemplarRepository;

    public List<EmprestimoResumoDTO> listarTodosResumidos() {
        return emprestimoRepository.findAll().stream()
                .map(this::toResumoDTO)
                .collect(Collectors.toList());
    }

    public EmprestimoDTO buscarPorId(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        return toDTO(emprestimo);
    }

    @Transactional
    public EmprestimoDTO criar(EmprestimoDTO emprestimoDTO) {
        Usuario usuario = usuarioRepository.findById(emprestimoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Exemplar exemplar = exemplarRepository.findById(emprestimoDTO.getExemplarId())
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setExemplar(exemplar);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(14));
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        return toDTO(emprestimoSalvo);
    }

    @Transactional
    public EmprestimoDTO devolver(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        emprestimo.setStatus(StatusEmprestimo.CONCLUIDO);

        return toDTO(emprestimoRepository.save(emprestimo));
    }

    @Transactional
    public void atualizarStatusAtrasados() {
        emprestimoRepository.atualizarStatusParaAtrasado();
    }

    public List<EmprestimoResumoDTO> listarPorUsuario(Long usuarioId) {
        return emprestimoRepository.findEmprestimosResumidosPorUsuario(usuarioId).stream()
                .map(this::mapToResumoDTO)
                .collect(Collectors.toList());
    }

    private EmprestimoDTO toDTO(Emprestimo emprestimo) {
        return EmprestimoDTO.builder()
                .id(emprestimo.getId())
                .usuarioId(emprestimo.getUsuario().getId())
                .exemplarId(emprestimo.getExemplar().getId())
                .dataEmprestimo(emprestimo.getDataEmprestimo())
                .dataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista())
                .dataDevolucaoReal(emprestimo.getDataDevolucaoReal())
                .status(emprestimo.getStatus())
                .build();
    }

    private EmprestimoResumoDTO toResumoDTO(Emprestimo emprestimo) {
        return EmprestimoResumoDTO.builder()
                .id(emprestimo.getId())
                .usuarioNome(emprestimo.getUsuario().getNome())
                .livroTitulo(emprestimo.getExemplar().getLivro().getTitulo())
                .dataEmprestimo(emprestimo.getDataEmprestimo())
                .dataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista())
                .status(emprestimo.getStatus())
                .build();
    }

    private EmprestimoResumoDTO mapToResumoDTO(Object[] result) {
        return EmprestimoResumoDTO.builder()
                .usuarioNome((String) result[0])
                .livroTitulo((String) result[1])
                .dataEmprestimo((LocalDate) result[2])
                .dataDevolucaoPrevista((LocalDate) result[3])
                .status(StatusEmprestimo.valueOf((String) result[4]))
                .build();
    }
}