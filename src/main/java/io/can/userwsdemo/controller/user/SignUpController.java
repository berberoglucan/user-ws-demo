package io.can.userwsdemo.controller.user;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.ui.request.UserSignUpRequestModel;
import io.can.userwsdemo.ui.response.UserRest;
import io.can.userwsdemo.mapper.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ProjectConstants.SIGN_UP_ENDPOINT)
public class SignUpController {

    private final UserService userService;

    private final ObjectModelMapper mapper;

    @PostMapping
    public ResponseEntity<UserRest> signUp(@RequestBody UserSignUpRequestModel userSignUpRequestModel) {
        UserDto userRequest = userSignUpRequestModel.toUserDto();
        // UserDto userRequest = mapper.map(userRequestModel, UserDto.class); // alternative
        UserDto createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(mapper.map(createdUser, UserRest.class));
    }

}
