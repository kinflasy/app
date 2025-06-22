package br.org.kinflasy.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

public abstract class BaseService<R extends JpaRepository<E, I>, D, E, I> {

    protected final R repository;

    private DtoService<R, D, E, I> dtoService;

    protected BaseService(@Autowired final R repository) {
        this.repository = repository;
        dtoService = new DtoService<>(this);
    }

    public abstract I getId(E item);

    public abstract D toDto(final E item);

    public DtoService<R, D, E, I> dto() {
        return dtoService;
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    @Transactional
    public E create(final E item) {
        return repository.save(item);
    }

    private boolean existsById(final I id) {
        return (id != null) && repository.existsById(id);
    }

    public boolean exists(final E item) {
        return existsById(getId(item));
    }

    public E findById(final I id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado"));
    }

    @Transactional
    public E update(final E entity) throws EntityNotFoundException {
        if (exists(entity)) {
            return repository.save(entity);
        }

        throw new EntityNotFoundException("ID não encontrado");
    }

    @Transactional
    public void delete(final I id) throws EntityNotFoundException {
        if (existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("ID não encontrado");
        }
    }
}
