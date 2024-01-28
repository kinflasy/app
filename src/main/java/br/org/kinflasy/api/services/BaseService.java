package br.org.kinflasy.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
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

    public abstract @NonNull Id getId(@NonNull Entity item);

    public abstract @Nullable DTO toNullableDTO(final @Nullable Entity item);

    public abstract @NonNull DTO toNonNullDTO(final @NonNull Entity item);

    public DtoService<Repository, DTO, Entity, Id> dto() {
        return dtoService;
    }

    public @NonNull List<Entity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public @NonNull Entity create(final @NonNull Entity item) {
        return repository.save(item);
    }

    private @NonNull Boolean existsById(final @NonNull Id id) {
        return (id != null) ? repository.existsById(id) : false;
    }

    public @NonNull Boolean exists(final @NonNull Entity item) {
        return existsById(getId(item));
    }

    public @NonNull Entity findById(final @NonNull Id id) throws EntityNotFoundException {
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
    public @NonNull Entity update(final @NonNull Entity entity) throws EntityNotFoundException {
        if (exists(entity)) {
            return repository.save(entity);
        }

        throw new EntityNotFoundException("ID não encontrado");
    }

    @Transactional
    public void delete(final @NonNull Id id) throws EntityNotFoundException {
        if (existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }
}
