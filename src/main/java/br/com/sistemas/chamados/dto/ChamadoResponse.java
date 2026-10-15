package br.com.sistemas.chamados.dto;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import java.time.LocalDate;

public record ChamadoResponse(
    Long id,
    String titulo,
    String descricao,
    LocalDate dataAbertura,
    LocalDate dataFechamento,
    Prioridade prioridade,
    StatusChamado status,
    String clienteNome,
    String categoriaNome,
    String tecnicoNome
) {
    public static ChamadoResponse de(Chamado c) {
        return new ChamadoResponse(
            c.getId(),
            c.getTitulo(),
            c.getDescricao(),
            c.getDataAbertura(),
            c.getDataFechamento(),
            c.getPrioridade(),
            c.getStatus(),
            c.getCliente() != null ? c.getCliente().getNome() : null,
            c.getCategoria() != null ? c.getCategoria().getNome() : null,
            c.getTecnico() != null ? c.getTecnico().getNome() : null
        );
    }
}