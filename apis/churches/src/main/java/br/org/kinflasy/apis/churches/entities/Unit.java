package br.org.kinflasy.apis.churches.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.churches.enums.UnitType;
import br.org.kinflasy.libs.contacts.contracts.Emailable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "units", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "church_id", "name" }),
        @UniqueConstraint(columnNames = { "church_id", "slug" })
})
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Unit extends AbstractSimpleAuditable implements Emailable {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Chaves "estrangeiras" (referências)
     */

    @Column(nullable = false)
    private UUID churchId;

    @Column(nullable = false)
    private UUID addressId;

    @Column
    private UUID profileImageId;

    @Column
    private UUID coverImageId;

    /*
     * Enumerações
     */

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitType type;

    /*
     * Dados primitivos
     */

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

}
