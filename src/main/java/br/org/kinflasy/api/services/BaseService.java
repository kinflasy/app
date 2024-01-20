package br.org.kinflasy.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public abstract class BaseService<Repository extends JpaRepository<Entity, Id>, DTO, Entity, Id> {

    protected final Repository repository;

    public abstract @NonNull Id getId(@NonNull Entity item);

    public abstract @Nullable DTO toNullableDTO(final @Nullable Entity item);

    public abstract @NonNull DTO toNonNullDTO(final @NonNull Entity item);

    protected BaseService(final Repository repository) {
        this.repository = repository;
    }

    public @NonNull List<Entity> findAll() {
        return repository.findAll();
    }

    @Transactional
    public @NonNull Entity create(@NonNull final Entity item) {
        return repository.save(item);
    }

    public @NonNull Entity findById(@NonNull final Id id) throws EntityNotFoundException {
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

    private @NonNull Boolean exists(@NonNull final Entity item) {
        final Id id = getId(item);
        return (id != null) ? repository.existsById(id) : false;
    }

    @Transactional
    public @NonNull Entity update(@NonNull final Entity item) throws EntityNotFoundException {
        if (exists(item)) {
            return repository.save(item);
        }

        throw new EntityNotFoundException("ID não encontrado");
    }

    @Transactional
    public void delete(@NonNull final Entity item) throws EntityNotFoundException {
        if (exists(item)) {
            repository.delete(item);
        } else {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }
}
