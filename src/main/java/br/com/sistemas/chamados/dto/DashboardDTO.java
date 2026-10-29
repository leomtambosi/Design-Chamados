package br.com.sistemas.chamados.dto;

import java.util.Map;

public class DashboardDTO {

    private long totalChamados;
    private Map<String, Long> chamadosPorStatus;
    private Map<String, Long> chamadosPorPrioridade;

    public DashboardDTO(
            long totalChamados,
            Map<String, Long> chamadosPorStatus,
            Map<String, Long> chamadosPorPrioridade) {
        this.totalChamados = totalChamados;
        this.chamadosPorStatus = chamadosPorStatus;
        this.chamadosPorPrioridade = chamadosPorPrioridade;
    }

    public long getTotalChamados() {
        return totalChamados;
    }

    public Map<String, Long> getChamadosPorStatus() {
        return chamadosPorStatus;
    }

    public Map<String, Long> getChamadosPorPrioridade() {
        return chamadosPorPrioridade;
    }
}