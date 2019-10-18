package io.can.userwsdemo.service.impl;

import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.entity.Role;
import io.can.userwsdemo.entity.User;
import io.can.userwsdemo.enumeration.RoleTypes;
import io.can.userwsdemo.repository.RoleRepository;
import io.can.userwsdemo.repository.UserRepository;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.util.GenerateStringUtil;
import io.can.userwsdemo.mapper.ObjectModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    private final ObjectModelMapper mapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GenerateStringUtil generateStringUtil;
    private final PasswordEncoder bCryptPasswordEncoder;

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
        newUser.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        // new sign-up user has ROLE_USER
        Role userRole = roleRepository.findRoleByRoleName(RoleTypes.USER.getRole());
        if (userRole == null) {
            userRole = roleRepository.save(new Role(RoleTypes.USER.getRole()));
        }
        newUser.addUserRoles(userRole);

        User savedUser = userRepository.save(newUser);
        return mapper.map(savedUser, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto getUserWithEmail(String email) {

        User existUser = userRepository.findUserByEmail(email);

        // TODO: Custom exception yaz
        if (existUser == null) {
            throw new RuntimeException("User is not found at given email : " + email);
        }
        return mapper.map(existUser, UserDto.class);
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

        // alternative 1 -> no need to write "ROLE_" prefix, because roles method put the "ROLE_" prefix
        String[] roles = foundUser.getRoles().stream()
                .map(Role::getRoleName)
                .toArray(String[]::new);

        // alternative 2 -> need to write "ROLE_" prefix, because type must be converted to GrantedAuthority collection and authorities method not put the "ROLE_" prefix

        /* List<GrantedAuthority> authorities = foundUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList()); */

        return org.springframework.security.core.userdetails.User.
                withUsername(foundUser.getEmail())
                .password(foundUser.getEncryptedPassword())
                .disabled(!foundUser.getActive())
                .accountLocked(foundUser.getLocked())
                .roles(roles)
                //.authorities(authorities)
                .build();

    }
}
