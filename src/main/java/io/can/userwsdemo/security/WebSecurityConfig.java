package io.can.userwsdemo.security;

import io.can.userwsdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static io.can.userwsdemo.ProjectConstants.*;
import static io.can.userwsdemo.enumeration.RoleTypes.*;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_ENDPOINT, LOGIN_ENDPOINT).permitAll()
                .anyRequest().hasAnyRole(USER.getRole(), ADMIN.getRole())
                .and()
                //.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProvider))  // -> spring security default login endpoint filter
                .addFilterBefore(new JwtAuthorizationFilter(userService, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // don't create a session for rest

    }

    // required for inject the authentication manager
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
