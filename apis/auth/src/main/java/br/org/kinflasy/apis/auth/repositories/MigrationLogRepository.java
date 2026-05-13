package br.org.kinflasy.apis.auth.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.kinflasy.apis.auth.dto.OpenFgaMigrationStatus;
import br.org.kinflasy.apis.auth.entities.OpenFgaMigrationLog;

@Repository
public interface MigrationLogRepository extends JpaRepository<OpenFgaMigrationLog, UUID> {

    boolean existsByVersion(int version);

    boolean existsByVersionAndStatus(int version, OpenFgaMigrationStatus status);

    Optional<OpenFgaMigrationLog> findByVersionAndStatus(int version, OpenFgaMigrationStatus status);

    List<OpenFgaMigrationLog> findAllByOrderByVersionAsc();

}
