package io.can.userwsdemo.controller.user;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.enumeration.ErrorMessages;
import io.can.userwsdemo.exception.LoginException;
import io.can.userwsdemo.security.JwtProvider;
import io.can.userwsdemo.ui.request.UserLoginRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.ArrayList;

import static io.can.userwsdemo.enumeration.ErrorMessages.*;

/**
 * This controller handles custom login endpoint instead of spring security's login endpoint
 * */
@RequiredArgsConstructor
@RestController
@RequestMapping(ProjectConstants.LOGIN_ENDPOINT)
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void login(@RequestBody UserLoginRequestModel loginRequestModel,
                        HttpServletResponse response) {

        // TODO: email icin validasyon ekle
        // create a login token
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestModel.getEmail(),
                        loginRequestModel.getPassword(),
                        new ArrayList<>());

        Authentication authenticatedUser = authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        // get authenticated user's principal and create jwt
        String token = jwtProvider.generateToken(authenticatedUser);

        // TODO: geriye status veya token json olarak donulebilir
        // add token in response header
        response.setHeader(ProjectConstants.AUTHORIZATION_HEADER_NAME, ProjectConstants.TOKEN_PREFIX + token);

    }

    private Authentication authenticate(UsernamePasswordAuthenticationToken authToken) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String email = (String) authToken.getPrincipal();
        try {
            return  authenticationManager.authenticate(authToken);
        } catch (DisabledException ex) {
            throw new LoginException(USER_DISABLED.withGiven(email), badRequest);
        } catch (LockedException ex) {
            throw new LoginException(USER_LOCKED.withGiven(email), badRequest);
        } catch (BadCredentialsException ex) {
            throw new LoginException(BAD_CREDENTIALS.withGiven(email), badRequest);
        }
    }

}
