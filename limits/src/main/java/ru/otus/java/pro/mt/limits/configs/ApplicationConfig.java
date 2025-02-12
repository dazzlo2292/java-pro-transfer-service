package ru.otus.java.pro.mt.limits.configs;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public GroupedOpenApi transfersApiV1() {
        final String[] packagesToScan = {"ru.otus.java.pro.mt.limits.controllers"};
        return GroupedOpenApi
                .builder()
                .group("1. mt-limits-transfers-v1")
                .packagesToScan(packagesToScan)
                .pathsToMatch("/api/v1/**")
                .addOpenApiCustomizer(
                        openApi -> openApi.info(
                                new Info()
                                        .title("Микросервис лимитов")
                                        .description("OTUS - МС Лимитов - Лимиты клиентов")
                                        .version("1.0.0")
                        )
                )
                .build();
    }
}
