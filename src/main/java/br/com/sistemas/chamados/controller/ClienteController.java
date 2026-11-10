package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.ClienteRequest;
import br.com.sistemas.chamados.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        if (!model.containsAttribute("clienteRequest")) {
            model.addAttribute("clienteRequest", new ClienteRequest("", "", ""));
        }
        return "cliente-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("clienteRequest") ClienteRequest dto, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente-form";
        }
        service.criar(dto);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        var cliente = service.buscar(id);
        // Mapeia o response para o request correspondente para edição
        ClienteRequest dto = new ClienteRequest(cliente.nome(), cliente.email(), cliente.telefone());
        model.addAttribute("clienteRequest", dto);
        model.addAttribute("clienteId", id);
        return "cliente-form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("clienteRequest") ClienteRequest dto, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente-form";
        }
        service.atualizar(id, dto);
        return "redirect:/clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/clientes";
    }
}