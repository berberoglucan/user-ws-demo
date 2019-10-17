package io.can.userwsdemo.controller;

import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.mapper.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
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

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }

}
