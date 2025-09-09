package br.org.kinflasy.libs.api_utils;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractSimpleAuditable<I extends Serializable> {

    @Column
    private I createdBy;

    @Column
    private LocalDateTime createdDate;

    @Column
    private I lastModifiedBy;

    @Column
    private LocalDateTime lastModifiedDate;

}
