package com.salvaroca.obrestdatajpa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Configuring OpenAPI - Swagger configuration with OpenAPI custom annotations
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Salva Roca",
                        url = "https://github.com/SalvaRoca"
                ),
                title = "Books Spring Boot API",
                version = "0.1",
                description = "Simple book repository REST API to learn Swagger documentation.",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                ),
                termsOfService = "http://swagger.io/terms/"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)

public class SwaggerConfig {

}

/*
Configuring OpenAPI - Swagger configuration without OpenAPI custom annotations
 */
//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Books Spring Boot API")
//                        .version("0.1")
//                        .description("Simple book repository REST API to learn Swagger documentation.")
//                        .termsOfService("http://swagger.io/terms/")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
//                );
//    }
//}
