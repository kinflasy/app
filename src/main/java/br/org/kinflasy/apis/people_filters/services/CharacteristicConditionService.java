package br.org.kinflasy.apis.people_filters.services;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people_filters.entities.CharacteristicCondition;
import br.org.kinflasy.apis.people_filters.repositories.CharacteristicConditionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CharacteristicConditionService {

    private final CharacteristicConditionRepository repository;

    public CharacteristicCondition findOrCreate(final CharacteristicCondition filter) {
        return repository.findOrCreate(filter);
    }

}
