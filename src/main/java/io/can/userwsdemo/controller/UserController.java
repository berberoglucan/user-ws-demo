package io.can.userwsdemo.controller;

import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.mapper.ObjectModelMapper;
import io.can.userwsdemo.ui.request.UserUpdateRequestModel;
import io.can.userwsdemo.ui.response.UserRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

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

    @PutMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> updateUser(@PathVariable(required = false) String userId,
                                               @RequestBody UserUpdateRequestModel userUpdateRequestModel) {
        UserDto userRequest = userUpdateRequestModel.toUserDto();
        UserDto updatedUser = userService.updateUser(userId, userRequest);
        UserRest userResponse = mapper.map(updatedUser, UserRest.class);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }

}
