package io.can.userwsdemo.controller.user;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.security.JwtProvider;
import io.can.userwsdemo.ui.request.UserLoginRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * This controller handles custom login endpoint instead of spring security's login endpoint
 * */
@RequiredArgsConstructor
@RestController
@RequestMapping(ProjectConstants.LOGIN_ENDPOINT)
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping
    public void login(@RequestBody UserLoginRequestModel loginRequestModel,
                        HttpServletResponse response) {

        // TODO: email icin validasyon ekle
        // create a login token
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestModel.getEmail(),
                        loginRequestModel.getPassword(),
                        new ArrayList<>());

        // authenticate user -> call loadUserByUsername
        Authentication authenticate = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // get authenticated user's principal and create jwt
        String token = jwtProvider.generateToken(authenticate);

        // add token in response header
        response.setHeader(ProjectConstants.AUTHORIZATION_HEADER_NAME, ProjectConstants.TOKEN_PREFIX + token);

    }

}
