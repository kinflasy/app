package br.org.kinflasy.apis.contacts.entities;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.kinflasy.libs.api_utils.AbstractSimpleAuditable;
import br.org.kinflasy.libs.people.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@DynamicUpdate
@Table(name = "email_addresses", indexes = { @Index(columnList = EmailAddress_.ADDRESS) })
@Data
@Accessors(chain = false)
@EqualsAndHashCode(callSuper = false)
public class EmailAddress extends AbstractSimpleAuditable {

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
    private String address;

    @Column
    private LocalDateTime verifiedAt;

    @Override
    @PrePersist
    protected void onPreCreate() {
        if (getCreatedBy() == null) {
            Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .map(Authentication::getPrincipal)
                    .filter(UserDto.class::isInstance)
                    .ifPresent(principal -> setCreatedBy(((UserDto) principal).getId()));
        }
        setCreatedDate(LocalDateTime.now());
    }

    @Override
    @PreUpdate
    protected void onPreUpdate() {
        Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(UserDto.class::isInstance)
                .ifPresent(principal -> setCreatedBy(((UserDto) principal).getId()));
        setCreatedDate(LocalDateTime.now());
    }

}
