package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.DashboardDTO;
import br.com.sistemas.chamados.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            DashboardDTO dto = dashboardService.obterDashboard();
            model.addAttribute("dashboard", dto);
        } catch (Exception e) {
            // Se ocorrer algum erro ou o banco estiver vazio, passamos o DTO preenchido com valores zerados/vázios
            DashboardDTO dtoVazio = new DashboardDTO(0L, Collections.emptyMap(), Collections.emptyMap());
            model.addAttribute("dashboard", dtoVazio);
        }
        return "dashboard"; // Procura o arquivo dashboard.html na pasta templates
    }
}