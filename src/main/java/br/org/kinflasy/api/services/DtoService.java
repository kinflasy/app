package br.org.kinflasy.api.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public class DtoService<Repository extends JpaRepository<Entity, Id>, DTO, Entity, Id> {

    protected final BaseService<Repository, DTO, Entity, Id> base;

    protected DtoService(final BaseService<Repository, DTO, Entity, Id> repository) {
        this.base = repository;
    }

    public DTO nonNull(final Entity item) {
        return base.toNonNullDTO(item);
    }

    public DTO nullable(final Entity item) {
        return base.toNullableDTO(item);
    }

    public List<DTO> nonNull(final List<Entity> list) {
        // Verificar se não tem nenhum item nulo na lista
        final var nonNull = list.stream().allMatch(item -> item != null);

        // Mapear conforme resultado
        List<DTO> result = list.stream()
                .map(nonNull ? base::toNonNullDTO : base::toNonNullDTO)
                .toList();

        return (result != null) ? result : new ArrayList<>();
    }

    public List<DTO> nullable(final List<Entity> list) {
        return (list != null) ? nonNull(list) : new ArrayList<>();
    }

    public List<DTO> findAll() {
        final var result = base.findAll().stream()
                .map(base::toNonNullDTO)
                .toList();

        return (result != null) ? result : new ArrayList<>();
    }

    @Transactional
    public DTO create(final Entity item) {
        return base.toNonNullDTO(base.create(item));
    }

    public DTO findById(final Id id) throws EntityNotFoundException {
        return base.toNonNullDTO(base.findById(id));
    }

    @Transactional
    public DTO update(final Entity entity) throws EntityNotFoundException {
        return base.toNonNullDTO(base.update(entity));
    }

}
