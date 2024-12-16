package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  // Indique que c'est une classe de configuration Spring
public class RestTemplateConfig {

    // Déclare le bean RestTemplate que Spring gérera et pourra injecter
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
