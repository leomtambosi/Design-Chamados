package br.com.sistemas.chamados.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sistemas.chamados.dto.ComentarioRequest;
import br.com.sistemas.chamados.dto.ComentarioResponse;
import br.com.sistemas.chamados.dto.AtribuirTecnicoRequest;
import br.com.sistemas.chamados.dto.AlterarStatusRequest;
import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.service.ChamadoService;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.repository.ClienteRepository;
import br.com.sistemas.chamados.repository.TecnicoRepository;
import br.com.sistemas.chamados.repository.CategoriaRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TecnicoRepository tecnicoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    // --- RENDERIZAR A TELA DE LISTAGEM ---
    @GetMapping
    public String listarView(Model model) {
        model.addAttribute("chamados", service.listar());
        return "chamados";
    }

    // --- FORMULÁRIO DE NOVO CHAMADO ---
    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        if (!model.containsAttribute("chamadoRequest")) {
            // Ajustado para os 6 parâmetros corretos do record (ex: título, descrição, prioridade, clienteId, tecnicoId, categoriaId)
            model.addAttribute("chamadoRequest", new ChamadoRequest("", "", null, null, null, null));
        }
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("tecnicos", tecnicoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "chamado-form";
    }

    @PostMapping("/salvar")
    public String salvarView(@Valid @ModelAttribute("chamadoRequest") ChamadoRequest dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteRepository.findAll());
            model.addAttribute("tecnicos", tecnicoRepository.findAll());
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "chamado-form";
        }
        service.criar(dto);
        return "redirect:/chamados";
    }

    @PostMapping("/{id}/alterar-status")
    public String alterarStatusView(@PathVariable Long id, @RequestParam("status") StatusChamado status) {
        service.alterarStatus(id, status);
        return "redirect:/chamados/" + id;
    }

    @GetMapping("/excluir/{id}")
    public String excluirView(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/chamados";
    }

    // --- DETALHES / COMENTÁRIOS ---
    @GetMapping("/{id}")
    public String buscarView(@PathVariable Long id, Model model) {
        ChamadoResponse chamado = service.buscar(id);
        model.addAttribute("chamado", chamado);
        return "detalhes-chamado";
    }
}