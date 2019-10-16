package io.can.userwsdemo.service.impl;

import io.can.userwsdemo.entity.Role;
import io.can.userwsdemo.entity.User;
import io.can.userwsdemo.enumeration.RoleTypes;
import io.can.userwsdemo.repository.RoleRepository;
import io.can.userwsdemo.repository.UserRepository;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.util.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final ObjectModelMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        // TODO: encrypted password kaldirilacak
        user.setEncryptedPassword("test");

        // new sign-up user has ROLE_USER
        Role userRole = roleRepository.findRoleByRoleName(RoleTypes.USER.getRole());
        if (userRole == null) {
            userRole = roleRepository.save(new Role(RoleTypes.USER.getRole()));
        }
        user.setUserRoles(userRole);

        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }
}
