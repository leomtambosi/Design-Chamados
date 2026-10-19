package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.service.ChamadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChamadoController.class)
public class ChamadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChamadoService chamadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deveRetornarListaDeChamadosComSucesso() throws Exception {
        ChamadoResponse responseMock = new ChamadoResponse(
                1L,
                "Teste",
                "Desc",
                LocalDate.now(),
                null,
                Prioridade.ALTA,
                StatusChamado.ABERTO,
                "Cliente",
                "Categoria",
                null
        );

        given(chamadoService.listar()).willReturn(Collections.singletonList(responseMock));

        mockMvc.perform(get("/chamados")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Teste"));
    }

    @Test
    public void deveRetornarErro404QuandoChamadoNaoExistir() throws Exception {
        given(chamadoService.buscar(99L)).willThrow(new RuntimeException("Chamado 99 não encontrado"));

        mockMvc.perform(get("/chamados/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}