package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.repository.ComentarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioRepository comentarioRepository;

    public ComentarioController(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    @GetMapping
    public String listarComentarios(Model model) {
        // Busca todos os comentários gravados no banco e envia para a view
        model.addAttribute("comentarios", comentarioRepository.findAll());
        return "comentarios"; // Procura comentarios.html na pasta templates
    }
}