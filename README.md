🛠️ Sistema de Gerenciamento de Chamados (Spring Boot MVC + Thymeleaf)
Sistema web completo para gestão de suporte técnico e chamados (Help Desk), desenvolvido com arquitetura Server-Side Rendered (SSR) utilizando Spring Boot 3, Thymeleaf, Bootstrap 5 e base de dados H2.

🚀 Tecnologias Utilizadas
Java 21

Spring Boot 3.3.0 (Spring MVC, Spring Data JPA, Bean Validation)

Thymeleaf (Motor de templates HTML)

Bootstrap 5 & Font Awesome (Interface gráfica responsiva e moderna)

H2 Database (Base de dados em memória para testes e desenvolvimento)

Maven (Gestão de dependências e build)

📋 Funcionalidades do Sistema
Dashboard Gerencial:

Visão geral com contadores em tempo real de chamados abertos, técnicos, clientes e categorias.

Gestão de Clientes (CRUD):

Cadastro, listagem, edição e exclusão de clientes.

Gestão de Técnicos (CRUD):

Cadastro de técnicos com definição de perfil (ADMIN ou TECNICO), listagem, edição e exclusão.

Gestão de Categorias (CRUD):

Organização de chamados por categorias personalizadas (ex: Software, Redes, Hardware).

Abertura e Controlo de Chamados:

Abertura de novos chamados vinculando cliente, técnico responsável, categoria e prioridade (BAIXA, MEDIA, ALTA).

Detalhes do Chamado: Visualização completa das informações e histórico de interações.

Alteração de Status Dinâmica: Atualização do estado do chamado diretamente pela interface (ABERTO, EM_ATENDIMENTO, AGUARDANDO_CLIENTE, FECHADO).

⚙️ Como Executar o Projeto
Certifique-se de ter o JDK 21 instalado no seu computador antes de prosseguir.

1. Clonar ou abrir o repositório
Abra o seu terminal na pasta raiz do projeto (Design-Chamados).

2. Compilar o projeto com o Maven
Execute o comando abaixo para limpar builds anteriores e gerar o pacote executável (ignorando testes unitários por agilidade):

PowerShell
.\mvnw clean package -DskipTests
3. Iniciar a aplicação
Após a conclusão bem-sucedida do build, execute o ficheiro JAR gerado na pasta target (utilizando a flag recomendada para o Java 21):

PowerShell
java "-Dnet.bytebuddy.experimental=true" -jar "target\chamados-0.0.1-SNAPSHOT.jar"
4. Aceder ao Sistema
Abra o seu navegador web de preferência e acesse o endereço principal:

http://localhost:8080/dashboard

📂 Estrutura de Rotas Principais (Web UI)
/dashboard — Painel de indicadores e estatísticas gerais.

/chamados — Listagem de todos os chamados abertos.

/chamados/novo — Formulário de abertura de novo chamado.

/clientes — Gestão e listagem de clientes.

/tecnicos — Gestão e listagem de técnicos.

/categorias — Gestão e listagem de categorias.