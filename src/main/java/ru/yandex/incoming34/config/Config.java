package ru.yandex.incoming34.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class Config {

    @Value("${app.items-per-page}")
    private String itemsPerPage;
    @Value("${app.token-retention-in-minutes}")
    private String tokenRetentionInMinutes;

    @Bean
    public Integer itemsPerPage(){
        return Integer.valueOf(itemsPerPage);
    }

    @Bean
    public Integer tokenRetentionInMinutes(){
        return Integer.valueOf(tokenRetentionInMinutes);
    }
}
