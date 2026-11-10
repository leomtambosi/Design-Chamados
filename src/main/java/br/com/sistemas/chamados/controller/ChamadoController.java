package br.com.sistemas.chamados.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import br.com.sistemas.chamados.dto.ComentarioRequest;
import br.com.sistemas.chamados.dto.ComentarioResponse;
import br.com.sistemas.chamados.dto.AtribuirTecnicoRequest;
import br.com.sistemas.chamados.dto.AlterarStatusRequest;
import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.service.ChamadoService;
import br.com.sistemas.chamados.entity.StatusChamado;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    // --- RENDERIZAR A TELA THYMELEAF ---
    @GetMapping
    public String listarView(Model model) {
        model.addAttribute("chamados", service.listar());
        return "chamados"; // Retorna o arquivo chamados.html
    }

    // --- ENDPOINTS DE API (JSON) ---
    @PostMapping
    @ResponseBody
    public ResponseEntity<ChamadoResponse> criar(
            @Valid @RequestBody ChamadoRequest dto) {

        ChamadoResponse criado = service.criar(dto);

        return ResponseEntity
                .created(URI.create("/chamados/" + criado.id()))
                .body(criado);
    }

    @PostMapping("/{id}/comentarios")
    @ResponseBody
    public ResponseEntity<ComentarioResponse> adicionarComentario(
            @PathVariable Long id,
            @Valid @RequestBody ComentarioRequest dto) {

        ComentarioResponse criado = service.adicionarComentario(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping(params = "api=true")
    @ResponseBody
    public List<ChamadoResponse> listarApi() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ChamadoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping(params = "tecnicoId")
    @ResponseBody
    public List<ChamadoResponse> listarPorTecnico(@RequestParam Long tecnicoId) {
        return service.listarPorTecnico(tecnicoId);
    }

    @GetMapping(params = "status")
    @ResponseBody
    public List<ChamadoResponse> listarPorStatus(@RequestParam StatusChamado status) {
        return service.listarPorStatus(status);
    }

    @PatchMapping("/{id}/atribuir")
    @ResponseBody
    public ChamadoResponse atribuirTecnico(
            @PathVariable Long id,
            @Valid @RequestBody AtribuirTecnicoRequest dto) {

        return service.atribuirTecnico(id, dto.tecnicoId());
    }

    @PatchMapping("/{id}/status")
    @ResponseBody
    public ChamadoResponse alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AlterarStatusRequest dto) {

        return service.alterarStatus(id, dto.status());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}