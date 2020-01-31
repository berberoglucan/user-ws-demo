package io.can.userwsdemo.service.impl;

import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.entity.Role;
import io.can.userwsdemo.entity.User;
import io.can.userwsdemo.enumeration.ErrorMessages;
import io.can.userwsdemo.enumeration.RoleTypes;
import io.can.userwsdemo.exception.UserServiceException;
import io.can.userwsdemo.repository.RoleRepository;
import io.can.userwsdemo.repository.UserRepository;
import io.can.userwsdemo.security.UserPrincipal;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.util.ServiceUtil;
import io.can.userwsdemo.mapper.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static io.can.userwsdemo.enumeration.ErrorMessages.*;
import static io.can.userwsdemo.enumeration.RoleTypes.*;


@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final ObjectModelMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ServiceUtil serviceUtil;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        User existUser = userRepository.findUserByEmail(userDto.getEmail());
        // Throw exception if there are exists user with this email
        if (existUser != null) {
            throw new UserServiceException(RECORD_ALREADY_EXISTS.withGiven(userDto.getEmail()), HttpStatus.BAD_REQUEST);
        }

        User newUser = mapper.map(userDto, User.class);
        newUser.setUserId(serviceUtil.generateUserId());
        newUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        // new sign-up user has ROLE_USER
        Role userRole = roleRepository.findRoleByRoleName(USER.getRole());
        if (userRole == null) {
            userRole = roleRepository.save(new Role(USER.getRole()));
        }
        newUser.addUserRoles(userRole);

        User savedUser = userRepository.save(newUser);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) {
        return (UserDto) this.getUserDtoOrUserByEmail(email, true);
    }

    @Override
    public Object getUserDtoOrUserByEmail(String email, boolean isUserDto) {

        User existUser = userRepository.findUserByEmail(email);
        if (existUser == null) {
            throw new UserServiceException(NO_RECORD_FOUND.withGiven(email), HttpStatus.NOT_FOUND);
        }
        if (!isUserDto) {
            return existUser;
        }

        return mapper.map(existUser, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto getUserByUserId(String userId) {
        Optional<User> userOpt = userRepository.findUserByUserId(userId);
        User user = userOpt.orElseThrow(() -> new UserServiceException(NO_RECORD_FOUND.withGiven(userId), HttpStatus.NOT_FOUND));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByLongId(String id) {
        Long longId = serviceUtil.getValidNumberId(id, Long.class);
        User foundUser = userRepository.findUserById(longId).orElseThrow(() -> new UserServiceException(NO_RECORD_FOUND.withGiven(id), HttpStatus.NOT_FOUND));
        return mapper.map(foundUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        return null;
    }

    /**
     * This method calls by spring security
     * */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {

        User foundUser = userRepository.findUserByEmail(email);

        if (foundUser == null) {
            throw new UsernameNotFoundException("No registered user with this email : " + email);
        }

        return new UserPrincipal(foundUser);
    }
}
