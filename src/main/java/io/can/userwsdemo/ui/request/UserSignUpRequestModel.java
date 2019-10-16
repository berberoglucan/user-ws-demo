package io.can.userwsdemo.ui.request;

import io.can.userwsdemo.dto.RoleDto;
import io.can.userwsdemo.dto.UserDto;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<RoleDto> roles;

    public UserDto toUserDto() {
        if (!CollectionUtils.isEmpty(roles)) {
             roles = roles.stream()
                    .filter(Objects::nonNull)
                    .peek(role -> role.setRoleName(role.getRoleName().toUpperCase()))
                    .filter(role -> !role.getRoleName().startsWith("ROLE_"))
                    .peek(role -> {
                        String roleName = role.getRoleName();
                        String newRoleName = roleName.replace(roleName, "ROLE_" + roleName);
                        role.setRoleName(newRoleName);
                    })
                     .collect(Collectors.toList());
        }
        return UserDto.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .roles(this.roles)
                .build();
    }

}
