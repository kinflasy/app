package br.org.kinflasy.apis.churches.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.org.kinflasy.apis.churches.entities.Church;
import br.org.kinflasy.apis.churches.entities.Church_;

public interface ChurchRepository extends JpaRepository<Church, UUID>, JpaSpecificationExecutor<Church> {

    public static Specification<Church> searchByTerm(final String term) {
        return (root, query, cb) -> {
            if (term == null || term.isBlank())
                return null;

            final var pattern = "%" + term.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get(Church_.NAME)), pattern),
                    cb.like(cb.lower(root.get(Church_.SLUG)), pattern),
                    cb.like(cb.lower(root.get(Church_.ACRONYM)), pattern));
        };
    }

}
