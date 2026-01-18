package br.org.kinflasy.libs.api_utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.people_filters.dto.SerializableCondition;

@Configuration
public class JacksonConfig {

    @Bean
    ObjectMapper customizeJackson() {
        return new ObjectMapper()
                .addMixIn(Condition.class, SerializableCondition.class);
    }

}
