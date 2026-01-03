package br.org.kinflasy.libs.api_utils;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.kinflasy.libs.people.dto.UserDto;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractSimpleAuditable {

    @CreatedBy
    private UUID createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private UUID lastModifiedBy;

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
