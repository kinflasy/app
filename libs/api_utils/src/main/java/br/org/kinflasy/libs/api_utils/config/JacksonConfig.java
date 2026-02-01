package br.org.kinflasy.libs.api_utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

    @Bean
    ObjectMapper customizeJackson() {
        return new ObjectMapper()
                .findAndRegisterModules();
    }

}
