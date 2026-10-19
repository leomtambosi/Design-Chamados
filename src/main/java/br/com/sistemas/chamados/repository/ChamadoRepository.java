package br.com.sistemas.chamados.repository;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByTecnicoId(Long tecnicoId);
    List<Chamado> findByStatus(StatusChamado status);
}