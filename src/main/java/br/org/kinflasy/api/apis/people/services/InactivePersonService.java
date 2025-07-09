package br.org.kinflasy.api.apis.people.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.org.kinflasy.api.apis.people.converters.InactivePersonConverter;
import br.org.kinflasy.api.apis.people.repositories.InactivePersonRepository;
import br.org.kinflasy.api.libs.people.dto.InactivePersonDto;
import br.org.kinflasy.api.libs.people.dto.InactivePersonRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InactivePersonService {

    private final InactivePersonRepository repository;
    private final InactivePersonConverter converter;

    public List<InactivePersonDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public InactivePersonDto create(final InactivePersonRequest.Create form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public InactivePersonDto findById(final UUID id) {
        final var entity = repository.findById(id);
        return converter.toDto(entity);
    }

    public InactivePersonDto update(final InactivePersonRequest.Update form) {
        final var entity = converter.toEntity(form);
        repository.save(entity);
        return converter.toDto(entity);
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
