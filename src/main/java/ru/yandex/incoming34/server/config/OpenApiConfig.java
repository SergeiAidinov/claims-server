package ru.yandex.incoming34.server.config;

import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

@Configuration
public class OpenApiConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.yandex.incoming34"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    ApiInfo apiInfo() {
        return new ApiInfo(
                "Swagger REST API для тестового задания",
                "Тествое задание",
                componentVersion(),
                null,
                new Contact("Sergei Aidinov", null, "incoming34@yandex.ru"),
                null, null, Collections.emptyList()
        );
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