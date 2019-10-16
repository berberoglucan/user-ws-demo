package io.can.userwsdemo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
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
}
