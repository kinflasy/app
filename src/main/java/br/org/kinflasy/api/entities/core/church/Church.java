package br.org.kinflasy.api.entities.core.church;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.AbstractAuditable;

import br.org.kinflasy.api.contracts.contact.Emailable;
import br.org.kinflasy.api.entities.core.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "churches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Church extends AbstractAuditable<User, Integer> implements Emailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
