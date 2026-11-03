package br.org.kinflasy.libs.api_utils;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.kinflasy.libs.people.dto.UserDto;
import br.org.kinflasy.libs.people.ids.PersonId;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = false)
@MappedSuperclass
public abstract class AbstractSimpleAuditable {

    @CreatedBy
    // todo substituir por UserId
    private PersonId createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    // todo substituir por UserId
    private PersonId lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @PrePersist
    protected void onPreCreate() {
        if (createdBy == null) {
            final var user = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            setCreatedBy(user.getId());
        }
        setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    protected void onPreUpdate() {
        if (lastModifiedBy == null) {
            final var user = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            setLastModifiedBy(user.getId());
        }
        setLastModifiedDate(LocalDateTime.now());
    }

}
