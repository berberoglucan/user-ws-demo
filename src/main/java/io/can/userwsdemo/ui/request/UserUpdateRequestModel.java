package io.can.userwsdemo.ui.request;

import io.can.userwsdemo.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserUpdateRequestModel {

    // userId is optional
    private String userId;

    @NotBlank(message = "Name is not be empty")
    @Min(value = 3L, message = "Name must be minimum 3 character")
    @Max(value = 10L, message = "Name must be maximum 10 character")
    private String firstName;

    @NotBlank(message = "Lastname is not be empty")
    @Min(value = 3L, message = "LastName must be minimum 3 character")
    @Max(value = 10L, message = "LastName must be maximum 10 character")
    private String lastName;

    public UserDto toUserDto() {
        return UserDto.builder()
                .userId(this.userId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }


}
