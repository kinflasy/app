package br.org.kinflasy.apis.churches.entities;

import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "churches")
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class Church extends AbstractSimpleAuditable {

    /*
     * Chave primária
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Dados primitivos
     */

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column
    private String acronym;

    @Column
    private String phone;

    @Column(nullable = false)
    private UUID emailId;

}
