package br.org.kinflasy.apis.auth.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

import br.org.kinflasy.apis.auth.dto.OpenFgaMigrationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "openfga_migrations_logs")
@Immutable
@Data
@Accessors(chain = false)
public class OpenFgaMigrationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = false)
    private int version;

    @Column(nullable = false)
    private String description;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OpenFgaMigrationStatus status;

    @Column(columnDefinition = "TEXT")
    private String details;

}
