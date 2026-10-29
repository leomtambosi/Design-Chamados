package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.service.ChamadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChamadoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ChamadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChamadoService chamadoService;

    @Test
    public void deveRetornarListaDeChamadosComSucesso() throws Exception {
        ChamadoResponse response = new ChamadoResponse(
                1L,
                "Teste de Chamado",
                "Descrição de teste",
                LocalDate.now(),
                null,
                Prioridade.MEDIA,
                StatusChamado.ABERTO,
                "Cliente Teste",
                "Categoria Teste",
                null
        );

        when(chamadoService.listar()).thenReturn(Arrays.asList(response));

        mockMvc.perform(get("/chamados"))
                .andExpect(status().isOk());
    }

    @Test
    public void deveRetornarErro404QuandoChamadoNaoExistir() throws Exception {
        when(chamadoService.buscar(anyLong()))
                .thenThrow(new RuntimeException("Chamado não encontrado"));

        mockMvc.perform(get("/chamados/999"))
                .andExpect(status().isNotFound());
    }
}