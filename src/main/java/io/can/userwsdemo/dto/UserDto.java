package io.can.userwsdemo.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = -1249403513568736320L;

    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus;
    private Boolean active;
    private Boolean locked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RoleDto> roles;

}
