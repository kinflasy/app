package br.org.kinflasy.execution.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.kinflasy.libs.people_filters.builder.implementations.ConditionBuilder;
import br.org.kinflasy.libs.people_filters.dto.SerializableCondition;
import br.org.kinflasy.libs.people_filters.enums.PersonCharacteristic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SerializableConditionTest {

    private static final String JSON = """
            {
              "type": "AND",
              "conditions": [
                {
                  "type": "CHARACTERISTIC",
                  "characteristic": "MALE"
                },
                {
                  "type": "IDENTITY",
                  "personId": "57b5fcea-b617-4b73-bf90-9d6388c88421"
                }
              ]
            }
                        """;

    @Autowired
    private ObjectMapper mapper;

    /*
     * java.lang.IllegalStateException: Failed to load ApplicationContext for
     * [WebMergedContextConfiguration@1d38ca2b testClass =
     * br.org.kinflasy.execution.dto.SerializableConditionTest, locations = [],
     * 
     * classes =
     * [br.org.kinflasy.MainApplication],
     * 
     * contextInitializerClasses = [],
     * activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties
     * =
     * ["org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true"
     * ], contextCustomizers = [org.springframework.boot.web.server.context.
     * SpringBootTestRandomPortContextCustomizerFactory$Customizer@664a9613,
     * org.springframework.boot.test.context.PropertyMappingContextCustomizer@0,
     * org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@
     * c65a5ef, org.springframework.boot.test.json.
     * 
     * DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer
     * 
     * @532a02d9, org.springframework.boot.test.autoconfigure.
     * OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer
     * 
     * @7cbee484,
     * org.springframework.test.context.support.DynamicPropertiesContextCustomizer@
     * 0, org.springframework.boot.test.context.SpringBootTestAnnotation@e5cc4af4],
     * resourceBasePath = "src/main/webapp", contextLoader =
     * org.springframework.boot.test.context.SpringBootContextLoader, parent = null]
     * 
     */
    @Test
    void constructTest() throws JsonProcessingException {
        final var innerCondition = ConditionBuilder.thePerson()
                .matchesAllConditions(thePerson -> thePerson
                        .is(PersonCharacteristic.MALE)
                        .is(UUID.fromString("57b5fcea-b617-4b73-bf90-9d6388c88421")))
                .build();

        final var expected = (innerCondition);

        final var json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expected);
        log.info(json);

        final var object = mapper.readValue(JSON, SerializableCondition.class);

        assertEquals(innerCondition, object);
    }

}
