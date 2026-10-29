package br.com.sistemas.chamados.service;

import br.com.sistemas.chamados.dto.DashboardDTO;
import br.com.sistemas.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final ChamadoRepository chamadoRepository;

    public DashboardService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    @Transactional(readOnly = true)
    public DashboardDTO obterDashboard() {

        Map<String, Long> porStatus = new LinkedHashMap<>();

        for (Object[] resultado : chamadoRepository.contarPorStatus()) {
            porStatus.put(
                resultado[0].toString(),
                ((Number) resultado[1]).longValue()
            );
        }

        Map<String, Long> porPrioridade = new LinkedHashMap<>();

        for (Object[] resultado : chamadoRepository.contarPorPrioridade()) {
            porPrioridade.put(
                resultado[0].toString(),
                ((Number) resultado[1]).longValue()
            );
        }

        return new DashboardDTO(
            chamadoRepository.count(),
            porStatus,
            porPrioridade
        );
    }
}