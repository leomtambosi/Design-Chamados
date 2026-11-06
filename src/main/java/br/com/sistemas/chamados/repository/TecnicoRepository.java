package br.com.sistemas.chamados.repository;

import br.com.sistemas.chamados.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Tecnico> findByEmail(String email);
}