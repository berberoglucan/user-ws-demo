package io.can.userwsdemo.ui.request;

import io.can.userwsdemo.dto.UserDto;
import lombok.*;

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

    public UserDto toUserDto() {
        return UserDto.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .build();
    }

}
