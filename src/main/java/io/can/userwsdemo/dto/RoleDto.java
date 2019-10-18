package io.can.userwsdemo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 1487920441932431190L;
    private Long roleId;
    private String roleName;

    public RoleDto(String roleName) {
        this.roleName = roleName;
    }
}
