package br.com.sistemas.chamados.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.sistemas.chamados.dto.AtribuirTecnicoRequest;
import br.com.sistemas.chamados.dto.AlterarStatusRequest;
import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.service.ChamadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponse> criar(
            @Valid @RequestBody ChamadoRequest dto) {

        ChamadoResponse criado = service.criar(dto);

        return ResponseEntity
                .created(URI.create("/chamados/" + criado.id()))
                .body(criado);
    }

    @GetMapping
    public List<ChamadoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ChamadoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PatchMapping("/{id}/atribuir")
    public ChamadoResponse atribuirTecnico(
            @PathVariable Long id,
            @Valid @RequestBody AtribuirTecnicoRequest dto) {

        return service.atribuirTecnico(id, dto.tecnicoId());
    }

    @PatchMapping("/{id}/status")
    public ChamadoResponse alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AlterarStatusRequest dto) {

        return service.alterarStatus(id, dto.status());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}