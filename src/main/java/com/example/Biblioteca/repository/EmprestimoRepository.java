package com.example.Biblioteca.repository;

import com.example.Biblioteca.model.Emprestimo;
import com.example.Biblioteca.Enum.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // 1. Consulta com JOIN FETCH e filtros múltiplos
    @Query("SELECT e FROM Emprestimo e " +
            "JOIN FETCH e.usuario " +
            "JOIN FETCH e.exemplar ex " +
            "JOIN FETCH ex.livro " +
            "WHERE e.status = :status AND e.dataEmprestimo BETWEEN :inicio AND :fim")
    List<Emprestimo> findCompletosPorStatusEPeriodo(
            @Param("status") StatusEmprestimo status,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    // 2. Atualização em massa com @Modifying
    @Modifying
    @Query("UPDATE Emprestimo e SET e.status = 'ATRASADO' " +
            "WHERE e.status = 'ATIVO' AND e.dataDevolucaoPrevista < CURRENT_DATE")
    void atualizarStatusParaAtrasado();

    // 3. Consulta com expressão condicional complexa
    @Query("SELECT e FROM Emprestimo e WHERE " +
            "(e.status = 'ATIVO' AND e.dataDevolucaoPrevista < CURRENT_DATE) OR " +
            "(e.status = 'ATRASADO' AND e.dataDevolucaoPrevista < :dataLimite)")
    List<Emprestimo> findEmprestimosAtrasadosOuMuitoAtrasados(
            @Param("dataLimite") LocalDate dataLimite);

    // 4. Consulta nativa com projeção customizada
    @Query(value =
            "SELECT u.nome as usuario, l.titulo as livro, " +
                    "e.data_emprestimo as dataEmprestimo, " +
                    "e.data_devolucao_prevista as dataPrevista, " +
                    "CASE WHEN e.status = 'ATIVO' AND e.data_devolucao_prevista < CURRENT_DATE " +
                    "THEN 'ATRASADO' ELSE e.status::text END as status " +
                    "FROM emprestimo e " +
                    "JOIN usuario u ON e.usuario_id = u.id " +
                    "JOIN exemplar ex ON e.exemplar_id = ex.id " +
                    "JOIN livro l ON ex.livro_id = l.id " +
                    "WHERE u.id = :usuarioId", nativeQuery = true)
    List<Object[]> findEmprestimosResumidosPorUsuario(@Param("usuarioId") Long usuarioId);

    // 5. Consulta com aggregate functions
    @Query(value = "SELECT AVG(TIMESTAMPDIFF(DAY, e.data_devolucao_prevista, e.data_devolucao_real)) " +
            "FROM emprestimo e WHERE e.usuario_id = :usuarioId AND e.data_devolucao_real IS NOT NULL",
            nativeQuery = true)
    Double calcularMediaAtrasoPorUsuario(@Param("usuarioId") long usuarioId);
}