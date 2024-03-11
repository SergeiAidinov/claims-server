package ru.yandex.incoming34.config;

import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.yandex.incoming34.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()))
                ;
    }

    @Bean
    ApiInfo apiInfo() {
        return new ApiInfo(
                "Swagger REST API для тестового задания",
                "Тестовое задание",
                componentVersion(),
                null,
                new Contact("Sergei Aidinov", null, "incoming34@yandex.ru"),
                null, null, Collections.emptyList()
        );
    }

    @Bean
    SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(
                new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private String componentVersion() {
        final String propertiesFileName = "pom.xml";
        String componentVersion = "Version is not specified";
        File file = new File(propertiesFileName);
        final XmlMapper xmlMapper = new XmlMapper();
        try {
            final JsonSchema jsonSchema = xmlMapper.generateJsonSchema(String.class);
            final JsonSchema json = xmlMapper.readValue(file, jsonSchema.getClass());
            componentVersion = Objects.nonNull(json.getSchemaNode().get("version"))
                    ? String.valueOf(json.getSchemaNode().get("version")).replaceAll("\"", "")
                    : componentVersion;
        } catch (Exception e) {
            return componentVersion;
        }
        return componentVersion;
    }
}