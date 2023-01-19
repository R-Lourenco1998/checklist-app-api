package com.learning.springboot.checklistapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ChecklistApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChecklistApiApplication.class, args);
    }

    @Profile("local")
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .maxAge(3600)
                        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization");
            }
        };
    }
    @Profile("aws")
    @Bean
    public WebMvcConfigurer corsAwsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .maxAge(3600)
                        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization");
            }
        };
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Checklist API")
                        .version("v1")
                        .description("Checklist API")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
