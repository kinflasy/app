package br.org.kinflasy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.slugify.Slugify;

@Configuration
public class SlugifyConfig {

    @Bean
    Slugify slugify() {
        return Slugify.builder()
                .lowerCase(true)
                .build();
    }

}
