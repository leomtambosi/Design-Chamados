package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.DashboardDTO;
import br.com.sistemas.chamados.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO obterDashboard() {
        return dashboardService.obterDashboard();
    }
}