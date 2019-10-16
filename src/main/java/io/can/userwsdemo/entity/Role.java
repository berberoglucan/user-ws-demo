package io.can.userwsdemo.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@ToString(exclude = {"users"})
public class Role implements Serializable {

    private static final long serialVersionUID = -4177414322385887478L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE) // not define setter for users
    private Set<User> users = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method manage the user and role tables bidirectional relationship via role entity
     * */
    public void addRoleUsers(User user) {
        getUsers().add(user);
        user.getRoles().add(this);
    }

    // TODO: TEST ET (Silinebilir)
    public void addRoleUsers(Collection<User> userCollection) {
        getUsers().addAll(userCollection);
        for (User user : userCollection) {
            user.getRoles().add(this);
        }
    }
}
