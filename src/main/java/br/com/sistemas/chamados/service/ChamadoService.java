package br.com.sistemas.chamados.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.entity.Categoria;
import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Cliente;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.entity.Tecnico;
import br.com.sistemas.chamados.exception.RecursoNaoEncontradoException;
import br.com.sistemas.chamados.exception.RegraNegocioException;
import br.com.sistemas.chamados.repository.CategoriaRepository;
import br.com.sistemas.chamados.repository.ChamadoRepository;
import br.com.sistemas.chamados.repository.ClienteRepository;
import br.com.sistemas.chamados.repository.TecnicoRepository;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final TecnicoRepository tecnicoRepository;

    public ChamadoService(
            ChamadoRepository chamadoRepository,
            ClienteRepository clienteRepository,
            CategoriaRepository categoriaRepository,
            TecnicoRepository tecnicoRepository) {

        this.chamadoRepository = chamadoRepository;
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Transactional
    public ChamadoResponse criar(ChamadoRequest dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente " + dto.clienteId() + " não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria " + dto.categoriaId() + " não encontrada"));

        Tecnico tecnico = null;

        if (dto.tecnicoId() != null) {
            tecnico = tecnicoRepository.findById(dto.tecnicoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Técnico " + dto.tecnicoId() + " não encontrado"));
        }

        Chamado chamado = new Chamado(
                dto.titulo(),
                dto.descricao(),
                dto.prioridade(),
                cliente,
                categoria,
                tecnico
        );

        Chamado salvo = chamadoRepository.save(chamado);

        return ChamadoResponse.de(salvo);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listar() {
        return chamadoRepository.findAll()
                .stream()
                .map(ChamadoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChamadoResponse buscar(Long id) {

        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Chamado " + id + " não encontrado"));

        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse atribuirTecnico(Long chamadoId, Long tecnicoId) {

        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Chamado " + chamadoId + " não encontrado"));

        if (chamado.getStatus() == StatusChamado.FECHADO) {
            throw new RegraNegocioException(
                    "Não é possível atribuir técnico a um chamado fechado");
        }

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Técnico " + tecnicoId + " não encontrado"));

        chamado.setTecnico(tecnico);

        return ChamadoResponse.de(chamadoRepository.save(chamado));
    }

    @Transactional
    public ChamadoResponse alterarStatus(
            Long chamadoId, StatusChamado novoStatus) {

        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Chamado " + chamadoId + " não encontrado"));

        if (chamado.getStatus() == StatusChamado.FECHADO) {
            throw new RegraNegocioException(
                    "Não é possível alterar o status de um chamado fechado");
        }

        chamado.setStatus(novoStatus);

        if (novoStatus == StatusChamado.FECHADO) {
            chamado.setDataFechamento(LocalDate.now());
        }

        return ChamadoResponse.de(chamadoRepository.save(chamado));
    }

    @Transactional
    public void excluir(Long id) {

        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Chamado " + id + " não encontrado"));

        chamadoRepository.delete(chamado);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listarPorTecnico(Long tecnicoId) {
        return chamadoRepository.findByTecnicoId(tecnicoId)
                .stream().map(ChamadoResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listarPorStatus(StatusChamado status) {
        return chamadoRepository.findByStatus(status)
                .stream().map(ChamadoResponse::de).toList();
    }
}