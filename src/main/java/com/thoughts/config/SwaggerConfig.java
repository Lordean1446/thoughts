package com.thoughts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Thoughts API")
                .version("1.0.0")
                .description("API para cadastro, consulta e gerenciamento de usuários e pensamentos. Projeto de aprendizado com Java e Spring Boot."));
    }
}

