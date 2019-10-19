package io.can.userwsdemo.ui.request;

import io.can.userwsdemo.dto.UserDto;
import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestModel {

    // TODO: Validasyon ekle
    private String email;
    private String password;

    public UserDto toUserDto() {
        return UserDto.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }

}
