package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.TecnicoRequest;
import br.com.sistemas.chamados.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tecnicos", service.listar());
        return "tecnicos";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        if (!model.containsAttribute("tecnicoRequest")) {
            model.addAttribute("tecnicoRequest", new TecnicoRequest("", "", null));
        }
        return "tecnico-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("tecnicoRequest") TecnicoRequest dto, BindingResult result) {
        if (result.hasErrors()) {
            return "tecnico-form";
        }
        service.criar(dto);
        return "redirect:/tecnicos";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        var tecnico = service.buscar(id);
        TecnicoRequest dto = new TecnicoRequest(tecnico.nome(), tecnico.email(), tecnico.perfil());
        model.addAttribute("tecnicoRequest", dto);
        model.addAttribute("tecnicoId", id);
        return "tecnico-form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("tecnicoRequest") TecnicoRequest dto, BindingResult result) {
        if (result.hasErrors()) {
            return "tecnico-form";
        }
        service.atualizar(id, dto);
        return "redirect:/tecnicos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/tecnicos";
    }
}