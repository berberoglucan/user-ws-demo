package io.can.userwsdemo.controller;

import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.ui.request.UserSignUpRequestModel;
import io.can.userwsdemo.ui.response.UserRest;
import io.can.userwsdemo.util.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final ObjectModelMapper mapper;
    private final UserService userService;

    @GetMapping
    public String getUser() {
        return "get user was called";
    }

    @PostMapping
    public ResponseEntity<UserRest> createUser(@RequestBody UserSignUpRequestModel userRequestModel) {
        UserDto userRequest;
        //userRequest = mapper.map(userRequestModel, UserDto.class);
        userRequest= userRequestModel.toUserDto();
        UserDto createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(mapper.map(createdUser, UserRest.class));
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }

}
