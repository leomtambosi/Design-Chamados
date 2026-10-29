package br.com.sistemas.chamados.repository;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByStatus(StatusChamado status);

    List<Chamado> findByTecnico_Id(Long tecnicoId);

    @Query("SELECT c.status, COUNT(c) FROM Chamado c GROUP BY c.status")
    List<Object[]> contarPorStatus();

    @Query("SELECT c.prioridade, COUNT(c) FROM Chamado c GROUP BY c.prioridade")
    List<Object[]> contarPorPrioridade();
}