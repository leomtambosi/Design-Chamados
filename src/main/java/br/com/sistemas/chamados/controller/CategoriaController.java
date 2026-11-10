package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.CategoriaRequest;
import br.com.sistemas.chamados.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", service.listar());
        return "categorias";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        if (!model.containsAttribute("categoriaRequest")) {
            model.addAttribute("categoriaRequest", new CategoriaRequest("", ""));
        }
        return "categoria-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("categoriaRequest") CategoriaRequest dto, BindingResult result) {
        if (result.hasErrors()) {
            return "categoria-form";
        }
        service.criar(dto);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        var categoria = service.buscar(id);
        CategoriaRequest dto = new CategoriaRequest(categoria.nome(), categoria.descricao());
        model.addAttribute("categoriaRequest", dto);
        model.addAttribute("categoriaId", id);
        return "categoria-form";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("categoriaRequest") CategoriaRequest dto, BindingResult result) {
        if (result.hasErrors()) {
            return "categoria-form";
        }
        service.atualizar(id, dto);
        return "redirect:/categorias";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/categorias";
    }
}