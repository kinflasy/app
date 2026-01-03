package br.org.kinflasy.apis.people_filters.controllers;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.people_filters.factories.ConditionFactory;
import br.org.kinflasy.apis.people_filters.services.ConditionService;
import br.org.kinflasy.libs.people.dto.PersonDto;
import br.org.kinflasy.libs.base_conditions.conditions.structure.Condition;
import br.org.kinflasy.libs.base_conditions.dto.ConditionRequest;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import br.org.kinflasy.libs.base_conditions.dto.StoredConditionDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/people-filters")
@Tag(name = "People Filter")
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class PeopleFilterController {

    private final ConditionFactory factory;
    private final ConditionService service;

    @PostMapping("test")
    public ResponseEntity<Boolean> test(@RequestBody PeopleFilterTestRequest request) {
        final var result = factory.getPredicate(request.getCondition()).test(request.getCondition(),
                request.getPerson());

        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}/test")
    public ResponseEntity<Boolean> test(@PathVariable @NotBlank UUID id, @RequestBody @NotNull PersonDto person) {
        return service.findById(id)
                .map(condition -> {
                    final var request = new PeopleFilterTestRequest(condition, person);
                    return test(request);
                })
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<StoredConditionDto<Condition>> findOrCreate(@RequestBody ConditionRequest request) {
        final var result = service.findOrCreate(request.getCondition());
        return ResponseEntity.ok(result);
    }

}
