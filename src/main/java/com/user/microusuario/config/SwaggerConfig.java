package com.user.microusuario.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI OpenAPI(){
        return new OpenAPI().info(new Info()
        .title("API Microservicio Usuario ")
        .version("1.0")
        .description("Este es el microservicio de usuario"));
    }

}
