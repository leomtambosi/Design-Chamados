package br.com.sistemas.chamados.repository;

import br.com.sistemas.chamados.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, Long id);
    Optional<Categoria> findByNome(String nome);
}