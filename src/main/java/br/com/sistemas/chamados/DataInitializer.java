
package br.com.sistemas.chamados;

import br.com.sistemas.chamados.entity.*;
import br.com.sistemas.chamados.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner carregarDados(
            CategoriaRepository categoriaRepository,
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            ChamadoRepository chamadoRepository,
            ComentarioRepository comentarioRepository) {

        return args -> carregarDadosIniciais(
                categoriaRepository,
                clienteRepository,
                tecnicoRepository,
                chamadoRepository,
                comentarioRepository
        );
    }

    @Transactional
    public void carregarDadosIniciais(
            CategoriaRepository categoriaRepository,
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            ChamadoRepository chamadoRepository,
            ComentarioRepository comentarioRepository) {

        // CATEGORIAS
        Categoria hardware = categoriaRepository.findByNome("Hardware")
                .orElseGet(() -> categoriaRepository.save(
                        new Categoria(
                                "Hardware",
                                "Equipamentos e componentes"
                        )
                ));

        Categoria software = categoriaRepository.findByNome("Software")
                .orElseGet(() -> categoriaRepository.save(
                        new Categoria(
                                "Software",
                                "Sistemas e aplicativos"
                        )
                ));

        Categoria rede = categoriaRepository.findByNome("Rede")
                .orElseGet(() -> categoriaRepository.save(
                        new Categoria(
                                "Rede",
                                "Conectividade e acesso à rede"
                        )
                ));

        // CLIENTES
        Cliente cliente1 = clienteRepository
                .findByEmail("ana.souza@example.com")
                .orElseGet(() -> clienteRepository.save(
                        new Cliente(
                                "Ana Souza",
                                "ana.souza@example.com",
                                "(47) 99911-1111"
                        )
                ));

        Cliente cliente2 = clienteRepository
                .findByEmail("bruno.lima@example.com")
                .orElseGet(() -> clienteRepository.save(
                        new Cliente(
                                "Bruno Lima",
                                "bruno.lima@example.com",
                                "(47) 99922-2222"
                        )
                ));

        Cliente cliente3 = clienteRepository
                .findByEmail("carla.mendes@example.com")
                .orElseGet(() -> clienteRepository.save(
                        new Cliente(
                                "Carla Mendes",
                                "carla.mendes@example.com",
                                "(47) 99933-3333"
                        )
                ));

        // TÉCNICOS
        Tecnico tecnico1 = tecnicoRepository
                .findByEmail("marcos.silva@example.com")
                .orElseGet(() -> tecnicoRepository.save(
                        new Tecnico(
                                "Marcos Silva",
                                "marcos.silva@example.com",
                                Perfil.TECNICO
                        )
                ));

        Tecnico tecnico2 = tecnicoRepository
                .findByEmail("juliana.costa@example.com")
                .orElseGet(() -> tecnicoRepository.save(
                        new Tecnico(
                                "Juliana Costa",
                                "juliana.costa@example.com",
                                Perfil.ADMIN
                        )
                ));

        // CHAMADOS
        Chamado chamado1 = obterOuCriarChamado(
                chamadoRepository,
                "DEMO - Computador não liga",
                "O computador não liga após uma queda de energia.",
                Prioridade.ALTA,
                cliente1,
                hardware,
                tecnico1
        );

        Chamado chamado2 = obterOuCriarChamado(
                chamadoRepository,
                "DEMO - Erro ao acessar o sistema",
                "O sistema apresenta erro durante o login.",
                Prioridade.MEDIA,
                cliente2,
                software,
                tecnico2
        );

        Chamado chamado3 = obterOuCriarChamado(
                chamadoRepository,
                "DEMO - Internet instável",
                "A conexão com a internet cai várias vezes ao longo do dia.",
                Prioridade.CRITICA,
                cliente3,
                rede,
                tecnico1
        );

        Chamado chamado4 = obterOuCriarChamado(
                chamadoRepository,
                "DEMO - Instalação de aplicativo",
                "Solicitação de instalação de um aplicativo de trabalho.",
                Prioridade.BAIXA,
                cliente1,
                software,
                tecnico2
        );

        Chamado chamado5 = obterOuCriarChamado(
                chamadoRepository,
                "DEMO - Teclado com defeito",
                "Algumas teclas não estão funcionando corretamente.",
                Prioridade.MEDIA,
                cliente2,
                hardware,
                null
        );

        // STATUS DOS CHAMADOS DE DEMONSTRAÇÃO
        chamado3.setStatus(StatusChamado.EM_ATENDIMENTO);
        chamado4.setStatus(StatusChamado.AGUARDANDO_CLIENTE);
        chamado5.setStatus(StatusChamado.FECHADO);

        if (chamado5.getDataFechamento() == null) {
            chamado5.setDataFechamento(java.time.LocalDate.now());
        }

        chamadoRepository.save(chamado3);
        chamadoRepository.save(chamado4);
        chamadoRepository.save(chamado5);

        // COMENTÁRIOS
        criarComentarioSeAusente(
                comentarioRepository,
                chamado1,
                "O chamado foi recebido e será analisado.",
                "Marcos Silva"
        );

        criarComentarioSeAusente(
                comentarioRepository,
                chamado2,
                "Estamos verificando os registros do sistema.",
                "Juliana Costa"
        );

        criarComentarioSeAusente(
                comentarioRepository,
                chamado3,
                "A equipe técnica está verificando a conexão.",
                "Marcos Silva"
        );

        System.out.println(
                "Carga inicial de demonstração concluída."
        );
    }

    private Chamado obterOuCriarChamado(
            ChamadoRepository chamadoRepository,
            String titulo,
            String descricao,
            Prioridade prioridade,
            Cliente cliente,
            Categoria categoria,
            Tecnico tecnico) {

        return chamadoRepository.findByTitulo(titulo)
                .orElseGet(() -> chamadoRepository.save(
                        new Chamado(
                                titulo,
                                descricao,
                                prioridade,
                                cliente,
                                categoria,
                                tecnico
                        )
                ));
    }

    private void criarComentarioSeAusente(
            ComentarioRepository comentarioRepository,
            Chamado chamado,
            String texto,
            String autor) {

        boolean existe = comentarioRepository
                .existsByChamadoIdAndTextoAndAutor(
                        chamado.getId(),
                        texto,
                        autor
                );

        if (!existe) {
            comentarioRepository.save(
                    new Comentario(texto, autor, chamado)
            );
        }
    }
}
