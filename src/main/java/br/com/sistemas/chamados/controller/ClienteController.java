package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        // Adiciona a lista de clientes ao modelo para o Thymeleaf renderizar na tabela
        model.addAttribute("clientes", service.listar());
        return "clientes"; // Vai procurar o arquivo clientes.html na pasta templates
    }
}