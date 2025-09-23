package br.org.kinflasy.apis.people_filters.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.kinflasy.apis.people_filters.factories.ConditionPredicateFactory;
import br.org.kinflasy.libs.people_filters.dto.PeopleFilterTestRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/core/people-filters")
@Tag(name = "People Filter")
@AllArgsConstructor
public class PeopleFilterController {

    private final ConditionPredicateFactory factory;

    @PostMapping("test")
    public ResponseEntity<Boolean> test(@RequestBody PeopleFilterTestRequest request) {
        final var result = factory.getPredicate(request.getCondition()).test(request.getCondition(),
                request.getPerson());

        return ResponseEntity.ok(result);
    }

}
