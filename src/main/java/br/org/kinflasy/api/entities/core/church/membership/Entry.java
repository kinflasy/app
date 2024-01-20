package br.org.kinflasy.api.entities.core.church.membership;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.lang.NonNull;

import br.org.kinflasy.api.entities.core.User;
import br.org.kinflasy.api.utils.enums.core.church.membership.EntryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class Entry extends AbstractAuditable<User, Integer>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @NonNull Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "membership_id")
    private @NonNull Membership membership;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private @NonNull EntryType type;

    @Column(name = "date", nullable = false)
    private @NonNull LocalDate date;
}
