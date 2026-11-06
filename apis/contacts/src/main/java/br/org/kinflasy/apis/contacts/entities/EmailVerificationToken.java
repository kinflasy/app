package br.org.kinflasy.apis.contacts.entities;

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

@Entity
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "email_verification_tokens")
public class EmailVerificationToken extends AbstractSimpleAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID emailId;

    @Column(nullable = false, unique = true)
    private String token;

}
