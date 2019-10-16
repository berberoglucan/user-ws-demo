package io.can.userwsdemo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false, of = "userId")
public class User extends BaseEntity {

    private static final long serialVersionUID = 9177995945992385152L;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password" ,nullable = false)
    private String encryptedPassword;

    @Column
    private String emailVerificationToken;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean emailVerificationStatus;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @PrePersist
    private void prePersistUser() {
        this.emailVerificationStatus = false;
        this.active = true;
    }
}
