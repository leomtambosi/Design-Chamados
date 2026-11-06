package br.com.sistemas.chamados.service;

import br.com.sistemas.chamados.dto.*;
import br.com.sistemas.chamados.entity.*;
import br.com.sistemas.chamados.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ComentarioRepository comentarioRepository;

    public ChamadoService(ChamadoRepository chamadoRepository,
                          ClienteRepository clienteRepository,
                          CategoriaRepository categoriaRepository,
                          TecnicoRepository tecnicoRepository,
                          ComentarioRepository comentarioRepository) {
        this.chamadoRepository = chamadoRepository;
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Transactional
    public ChamadoResponse criar(ChamadoRequest dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente " + dto.clienteId() + " não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria " + dto.categoriaId() + " não encontrada"));

        Tecnico tecnico = null;
        if (dto.tecnicoId() != null) {
            tecnico = tecnicoRepository.findById(dto.tecnicoId())
                    .orElseThrow(() -> new RuntimeException("Técnico " + dto.tecnicoId() + " não encontrado"));
        }

        Chamado chamado = new Chamado(
                dto.titulo(),
                dto.descricao(),
                dto.prioridade(),
                cliente,
                categoria,
                tecnico
        );

        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
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
                .orElseThrow(() -> new RuntimeException("Chamado " + id + " não encontrado"));
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse atribuirTecnico(Long id, Long tecnicoId) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado " + id + " não encontrado"));

        if (chamado.getStatus() == StatusChamado.FECHADO) {
            throw new RuntimeException("Não é possível alterar um chamado fechado.");
        }

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico " + tecnicoId + " não encontrado"));

        chamado.setTecnico(tecnico);
        chamadoRepository.save(chamado);

        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse alterarStatus(Long id, StatusChamado novoStatus) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado " + id + " não encontrado"));

        if (chamado.getStatus() == StatusChamado.FECHADO) {
            throw new RuntimeException("Não é possível alterar um chamado que já está fechado.");
        }

        chamado.setStatus(novoStatus);

        if (novoStatus == StatusChamado.FECHADO) {
            chamado.setDataFechamento(LocalDateTime.now().toLocalDate());
        }

        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listarPorTecnico(Long tecnicoId) {
        return chamadoRepository.findByTecnico_Id(tecnicoId)
                .stream()
                .map(ChamadoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listarPorStatus(StatusChamado status) {
        return chamadoRepository.findByStatus(status)
                .stream()
                .map(ChamadoResponse::de)
                .toList();
    }

    @Transactional
    public ComentarioResponse adicionarComentario(Long chamadoId, ComentarioRequest dto) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new RuntimeException("Chamado " + chamadoId + " não encontrado"));

        Comentario comentario = new Comentario(dto.texto(), dto.autor(), chamado);
        comentarioRepository.save(comentario);

        return ComentarioResponse.de(comentario);
    }

    @Transactional
    public void excluir(Long id) {
        if (!chamadoRepository.existsById(id)) {
            throw new RuntimeException("Chamado " + id + " não encontrado");
        }
        chamadoRepository.deleteById(id);
    }
}