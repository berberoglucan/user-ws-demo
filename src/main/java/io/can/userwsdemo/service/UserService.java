package io.can.userwsdemo.service;

import io.can.userwsdemo.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    Object getUserDtoOrUserByEmail(String email, boolean isUserDto);

    UserDto getUserByUserId(String userId);
}
