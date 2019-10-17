package io.can.userwsdemo.config;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.enumeration.RoleTypes;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userService;
    private final PasswordEncoder bCryptPasswordEncoder;

    // constructor injection
    public WebSecurityConfig(UserDetailsService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // TODO: formLogin() kaldiralacak. Sadece test icin eklendi
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, ProjectConstants.SIGN_UP_ENDPOINT).permitAll()
                //.antMatchers("/login").permitAll()
                //.antMatchers("/user").hasAnyRole(RoleTypes.USER.getRole(), RoleTypes.ADMIN.getRole())
                .anyRequest().hasAnyRole(RoleTypes.USER.getRole(), RoleTypes.ADMIN.getRole())
                .and().formLogin();
    }
}
