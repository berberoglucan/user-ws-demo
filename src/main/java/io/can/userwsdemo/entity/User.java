package io.can.userwsdemo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode(callSuper = false, of = "userId")
@ToString(exclude = {"roles"})
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

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    @Setter(AccessLevel.NONE) // not define setter for roles
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    private void prePersistUser() {
        this.emailVerificationStatus = false;
        this.active = true;
    }

    /**
     This method manage the user and role tables bidirectional relationship via user entity
    */
    public void setUserRoles(Role role) {
        getRoles().add(role);
        role.getUsers().add(this);
    }

    // TODO: TEST ET (Silinebilir)
    public void setUserRoles(Collection<Role> roleCollection) {
        getRoles().addAll(roleCollection);
        for (Role role : roleCollection) {
            role.getUsers().add(this);
        }
    }

}
