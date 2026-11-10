package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        // Busca a lista de categorias do serviço e adiciona no modelo para a view
        model.addAttribute("categorias", service.listar());
        return "categorias"; // Vai procurar o arquivo categorias.html na pasta templates
    }
}