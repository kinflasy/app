package br.org.kinflasy.apis.people_filters.services;

import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.people_filters.entities.StoredCharacteristicCondition;
import br.org.kinflasy.apis.people_filters.repositories.StoredCharacteristicConditionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CharacteristicConditionService {

    private final StoredCharacteristicConditionRepository repository;

    public StoredCharacteristicCondition findOrCreate(final StoredCharacteristicCondition filter) {
        return repository.findOrCreate(filter);
    }

}
