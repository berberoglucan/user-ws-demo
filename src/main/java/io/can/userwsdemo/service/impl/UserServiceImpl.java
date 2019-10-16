package io.can.userwsdemo.service.impl;

import io.can.userwsdemo.repository.UserRepository;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.util.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final ObjectModelMapper mapper;

    //private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }
}
