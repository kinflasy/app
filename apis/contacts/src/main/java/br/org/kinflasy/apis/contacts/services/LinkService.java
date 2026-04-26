package br.org.kinflasy.apis.contacts.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.contacts.entities.Link;
import br.org.kinflasy.apis.contacts.repositories.LinkRepository;
import br.org.kinflasy.libs.contacts.dto.LinkDto;
import br.org.kinflasy.libs.contacts.dto.LinkRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LinkService {

    private final ModelMapper mapper;

    private final LinkRepository repository;

    public LinkDto create(final LinkRequest request) {
        final var entity = mapper.map(request, Link.class);
        final var saved = repository.save(entity);
        return mapper.map(saved, LinkDto.class);
    }

    public Optional<LinkDto> findById(final UUID id) {
        return repository.findById(id)
                .map(link -> mapper.map(link, LinkDto.class));
    }

    public Optional<LinkDto> update(final UUID id, final LinkRequest request) {
        return repository.findById(id)
                .map(link -> {
                    mapper.map(request, link);
                    return repository.save(link);
                })
                .map(link -> mapper.map(link, LinkDto.class));
    }

    public void delete(final UUID id) {
        repository.deleteById(id);
    }

}
