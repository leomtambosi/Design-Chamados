package br.com.sistemas.chamados.service;

import br.com.sistemas.chamados.dto.DashboardDTO;
import br.com.sistemas.chamados.repository.ChamadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
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
        List<Object[]> statusResults = chamadoRepository.contarPorStatus();
        
        if (statusResults != null) {
            for (Object[] resultado : statusResults) {
                if (resultado != null && resultado[0] != null && resultado[1] != null) {
                    porStatus.put(resultado[0].toString(), ((Number) resultado[1]).longValue());
                }
            }
        }

        Map<String, Long> porPrioridade = new LinkedHashMap<>();
        List<Object[]> prioridadeResults = chamadoRepository.contarPorPrioridade();
        
        if (prioridadeResults != null) {
            for (Object[] resultado : prioridadeResults) {
                if (resultado != null && resultado[0] != null && resultado[1] != null) {
                    porPrioridade.put(resultado[0].toString(), ((Number) resultado[1]).longValue());
                }
            }
        }

        long total = chamadoRepository.count();

        return new DashboardDTO(total, porStatus, porPrioridade);
    }
}