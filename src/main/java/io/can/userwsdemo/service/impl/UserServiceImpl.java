package io.can.userwsdemo.service.impl;

import io.can.userwsdemo.entity.Role;
import io.can.userwsdemo.entity.User;
import io.can.userwsdemo.enumeration.RoleTypes;
import io.can.userwsdemo.repository.RoleRepository;
import io.can.userwsdemo.repository.UserRepository;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.util.GenerateStringUtil;
import io.can.userwsdemo.util.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final ObjectModelMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GenerateStringUtil generateStringUtil;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        User existUser = userRepository.findUserByEmail(userDto.getEmail());
        // Throw exception if there are exists user with this email
        // TODO: Custom exception yaz
        if (existUser != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = mapper.map(userDto, User.class);
        newUser.setUserId(generateStringUtil.generateUserId());

        // TODO: encrypted password kaldirilacak
        newUser.setEncryptedPassword("test");

        // new sign-up user has ROLE_USER
        Role userRole = roleRepository.findRoleByRoleName(RoleTypes.USER.getRole());
        if (userRole == null) {
            userRole = roleRepository.save(new Role(RoleTypes.USER.getRole()));
        }
        newUser.addUserRoles(userRole);

        User savedUser = userRepository.save(newUser);
        return mapper.map(savedUser, UserDto.class);
    }
}
