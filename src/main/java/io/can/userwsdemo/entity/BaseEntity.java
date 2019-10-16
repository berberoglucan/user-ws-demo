package io.can.userwsdemo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    protected Long id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @PrePersist
    protected void setCreatedAt() {
        this.createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    protected void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

}
