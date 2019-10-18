package io.can.userwsdemo.ui.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor()
public class UserRest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;

}
