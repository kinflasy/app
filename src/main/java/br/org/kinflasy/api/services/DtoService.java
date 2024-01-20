package br.org.kinflasy.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public class DtoService<Repository extends JpaRepository<Entity, Id>, DTO, Entity, Id> {

    protected final BaseService<Repository, DTO, Entity, Id> base;

    protected DtoService(final BaseService<Repository, DTO, Entity, Id> repository) {
        this.base = repository;
    }

    public @NonNull List<DTO> findAll() {
        final var result = base.findAll().stream()
                .map(base::toNonNullDTO)
                .toList();

        return (result != null) ? result : new ArrayList<>();
    }

    @Transactional
    public @NonNull DTO create(@NonNull final Entity item) {
        return base.toNonNullDTO(base.create(item));
    }

    public @NonNull DTO findById(@NonNull final Id id) throws EntityNotFoundException {
        return base.toNonNullDTO(base.findById(id));
    }

    @Transactional
    public @NonNull DTO update(@NonNull final Entity entity) throws EntityNotFoundException {
        return base.toNonNullDTO(base.update(entity));
    }

}
