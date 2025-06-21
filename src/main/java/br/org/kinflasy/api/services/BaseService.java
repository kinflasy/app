package br.org.kinflasy.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public abstract class BaseService<Repository extends JpaRepository<Entity, Id>, DTO, Entity, Id> {

    protected final Repository repository;

    private DtoService<Repository, DTO, Entity, Id> dtoService;

    protected BaseService(@Autowired final Repository repository) {
        this.repository = repository;
        dtoService = new DtoService<>(this);
    }

    public abstract Id getId(Entity item);

    public abstract @Nullable DTO toNullableDTO(final @Nullable Entity item);

    public abstract DTO toNonNullDTO(final Entity item);

    public DtoService<Repository, DTO, Entity, Id> dto() {
        return dtoService;
    }

    public List<Entity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Entity create(final Entity item) {
        return repository.save(item);
    }

    private Boolean existsById(final Id id) {
        return (id != null) ? repository.existsById(id) : false;
    }

    public Boolean exists(final Entity item) {
        return existsById(getId(item));
    }

    public Entity findById(final Id id) throws EntityNotFoundException {
        try {
            final var result = repository.findById(id).get();

            if (result == null) {
                throw new EntityNotFoundException("ID não encontrado");
            }

            return result;
        } catch (final NoSuchElementException | EntityNotFoundException e) {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }

    @Transactional
    public Entity update(final Entity entity) throws EntityNotFoundException {
        if (exists(entity)) {
            return repository.save(entity);
        }

        throw new EntityNotFoundException("ID não encontrado");
    }

    @Transactional
    public void delete(final Id id) throws EntityNotFoundException {
        if (existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }
}
