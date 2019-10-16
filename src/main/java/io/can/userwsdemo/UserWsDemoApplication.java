package io.can.userwsdemo;

import io.can.userwsdemo.entity.Role;
import io.can.userwsdemo.entity.User;
import io.can.userwsdemo.enumeration.RoleTypes;
import io.can.userwsdemo.repository.RoleRepository;
import io.can.userwsdemo.repository.UserRepository;
import io.can.userwsdemo.util.GenerateStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;


@SpringBootApplication
public class UserWsDemoApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenerateStringUtil generateStringUtil;

    public static void main(String[] args) {
        SpringApplication.run(UserWsDemoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role adminRole = insertRolesToDBOnRoleTypes(RoleTypes.values());
        Role userRole = roleRepository.findRoleByRoleName(RoleTypes.USER.getRole());

        User user1 = new User();
        user1.setFirstName("can");
        user1.setLastName("berberoglu");
        user1.setUserId(generateStringUtil.generateUserId());
        user1.setEmail("can@gmail.com");
        user1.setEncryptedPassword("123456");
        user1.addUserRoles(adminRole);

        User user2 = new User();
        user2.setFirstName("cem");
        user2.setLastName("berberoglu");
        user2.setUserId(generateStringUtil.generateUserId());
        user2.setEmail("cem@gmail.com");
        user2.setEncryptedPassword("123456");
        user2.addUserRoles(userRole);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    private Role insertRolesToDBOnRoleTypes(RoleTypes[] roleTypes) {
        for (RoleTypes roleType : roleTypes) {
            String roleName = roleType.getRole();
            Role role = roleRepository.findRoleByRoleName(roleName);
            if (role == null) {
                role = new Role(roleName);
                roleRepository.save(role);
            }
        }
        return roleRepository.findRoleByRoleName(RoleTypes.ADMIN.getRole());
    }
}
