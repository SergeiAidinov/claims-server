package ru.yandex.incoming34.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${app.items-per-page}")
    private String itemsPerPage;

    @Bean
    Integer itemsPerPage(){
        return Integer.valueOf(itemsPerPage);
    }
}
