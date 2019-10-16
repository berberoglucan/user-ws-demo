package io.can.userwsdemo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
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
    private Set<User> users = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
