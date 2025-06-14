package com.example.Biblioteca.repository;

import com.example.Biblioteca.model.Livro;
import com.example.Biblioteca.Enum.GeneroLiterario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // 1. Consulta com JOIN FETCH para evitar N+1
    @Query("SELECT DISTINCT l FROM Livro l " +
            "LEFT JOIN FETCH l.autorPrincipal " +
            "LEFT JOIN FETCH l.coautores " +
            "WHERE l.genero = :genero")
    List<Livro> findByGeneroWithAutores(@Param("genero") GeneroLiterario genero);

    // 2. Consulta com Subquery para livros com mais de X exemplares
    @Query("SELECT l FROM Livro l WHERE " +
            "(SELECT COUNT(e) FROM Exemplar e WHERE e.livro = l) > :minExemplares")
    List<Livro> findLivrosComMaisExemplaresQue(@Param("minExemplares") int minExemplares);

    // 3. Consulta com GROUP BY e HAVING para estatísticas de gêneros
    @Query("SELECT l.genero, COUNT(l), AVG(l.anoPublicacao) " +
            "FROM Livro l GROUP BY l.genero HAVING COUNT(l) > :minimo")
    List<Object[]> estatisticasPorGenero(@Param("minimo") long minimo);

    // 4. Consulta com CASE expression para status de disponibilidade
    @Query("SELECT l, " +
            "CASE WHEN (SELECT COUNT(e) FROM Exemplar e WHERE e.livro = l AND " +
            "NOT EXISTS (SELECT emp FROM Emprestimo emp WHERE emp.exemplar = e AND emp.status = 'ATIVO')) > 0 " +
            "THEN 'DISPONIVEL' ELSE 'INDISPONIVEL' END " +
            "FROM Livro l WHERE l.id = :id")
    Optional<Object[]> findLivroComStatusDisponibilidade(@Param("id") Long id);

    // 5. Consulta com paginação e ordenação complexa
    @Query("SELECT l FROM Livro l " +
            "WHERE (:genero IS NULL OR l.genero = :genero) " +
            "AND (:anoMin IS NULL OR l.anoPublicacao >= :anoMin) " +
            "AND (:anoMax IS NULL OR l.anoPublicacao <= :anoMax) " +
            "ORDER BY " +
            "CASE WHEN :ordenarPor = 'titulo' THEN l.titulo END ASC, " +
            "CASE WHEN :ordenarPor = 'ano' THEN l.anoPublicacao END DESC")
    List<Livro> findLivrosFiltrados(
            @Param("genero") GeneroLiterario genero,
            @Param("anoMin") Integer anoMin,
            @Param("anoMax") Integer anoMax,
            @Param("ordenarPor") String ordenarPor);
}