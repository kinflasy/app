package br.org.kinflasy.api.apis.churches.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.apis.people.entities.User;
import br.org.kinflasy.api.libs.contacts.contracts.Emailable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "churches")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Church extends AbstractAuditable<User, UUID> implements Emailable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column
    private String acronym;

    @Column
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private LocalDateTime emailVerifiedAt;

    @OneToMany(mappedBy = "church", cascade = CascadeType.ALL)
    private List<Unit> units;

}
