package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.service.TecnicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        // Adiciona a lista de técnicos ao modelo para renderizar na tabela HTML
        model.addAttribute("tecnicos", service.listar());
        return "tecnicos"; // Vai procurar o arquivo tecnicos.html na pasta templates
    }
}