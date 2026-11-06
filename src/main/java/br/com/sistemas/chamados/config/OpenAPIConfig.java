package br.com.sistemas.chamados.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI configurarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestão de Chamados")
                        .version("1.0.0")
                        .description(
                                "API para gerenciamento de chamados, clientes, " +
                                "categorias, técnicos e dashboard."
                        ));
    }
}