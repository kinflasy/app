package br.org.kinflasy.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public class DtoService<R extends JpaRepository<E, I>, D, E, I> {

    protected final BaseService<R, D, E, I> base;

    protected DtoService(final BaseService<R, D, E, I> repository) {
        this.base = repository;
    }

    public D toDto(final E item) {
        return base.toDto(item);
    }

    public List<D> toDto(final List<E> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .map(base::toDto)
                .toList();
    }

    public List<D> findAll() {
        final var result = base.findAll().stream()
                .map(base::toDto)
                .toList();

        return (result != null) ? result : new ArrayList<>();
    }

    @Transactional
    public D create(final E item) {
        return base.toDto(base.create(item));
    }

    public D findById(final I id) throws EntityNotFoundException {
        return base.toDto(base.findById(id));
    }

    @Transactional
    public D update(final E entity) throws EntityNotFoundException {
        return base.toDto(base.update(entity));
    }

}
