package br.com.sistemas.chamados.repository;

import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Cliente;
import br.com.sistemas.chamados.entity.Categoria;
import br.com.sistemas.chamados.entity.Prioridade;
import br.com.sistemas.chamados.entity.StatusChamado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChamadoRepositoryTest {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void deveSalvarEEncontrarChamadoPorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente = clienteRepository.save(cliente);

        Categoria categoria = new Categoria();
        categoria.setNome("Infraestrutura");
        categoria = categoriaRepository.save(categoria);

        Chamado chamado = new Chamado();
        chamado.setTitulo("Erro de Conexão");
        chamado.setDescricao("Falha ao conectar no banco");
        chamado.setDataAbertura(LocalDate.now());
        chamado.setPrioridade(Prioridade.ALTA);
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setCliente(cliente);
        chamado.setCategoria(categoria);

        Chamado salvo = chamadoRepository.save(chamado);

        assertThat(salvo.getId()).isNotNull();
        
        Optional<Chamado> encontrado = chamadoRepository.findById(salvo.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTitulo()).isEqualTo("Erro de Conexão");
    }

    @Test
    public void deveBuscarChamadosPorStatus() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria Souza");
        cliente.setEmail("maria@email.com");
        cliente = clienteRepository.save(cliente);

        Categoria categoria = new Categoria();
        categoria.setNome("Software");
        categoria = categoriaRepository.save(categoria);

        Chamado chamado1 = new Chamado();
        chamado1.setTitulo("Chamado 1");
        chamado1.setDescricao("Desc 1");
        chamado1.setDataAbertura(LocalDate.now());
        chamado1.setPrioridade(Prioridade.MEDIA);
        chamado1.setStatus(StatusChamado.ABERTO);
        chamado1.setCliente(cliente);
        chamado1.setCategoria(categoria);
        
        chamadoRepository.save(chamado1);

        List<Chamado> abertos = chamadoRepository.findByStatus(StatusChamado.ABERTO);

        assertThat(abertos).isNotEmpty();
        assertThat(abertos.get(0).getStatus()).isEqualTo(StatusChamado.ABERTO);
    }
}