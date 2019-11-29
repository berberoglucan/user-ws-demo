package io.can.userwsdemo.controller;

import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.mapper.ObjectModelMapper;
import io.can.userwsdemo.ui.response.UserRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final ObjectModelMapper mapper;
    private final UserService userService;

    // TODO: Daha sonra role based annotation ekle metotlara erisim icin

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        UserRest userResponse = mapper.map(userDto, UserRest.class);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(value = "/normal/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> getUserByNormalId(@PathVariable String id) {
        UserDto userDto = userService.getUserByLongId(id);
        UserRest userResponse = mapper.map(userDto, UserRest.class);
        return ResponseEntity.ok(userResponse);
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
