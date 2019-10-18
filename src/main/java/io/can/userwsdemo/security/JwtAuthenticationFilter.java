package io.can.userwsdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.dto.UserDto;
import io.can.userwsdemo.service.UserService;
import io.can.userwsdemo.ui.request.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
// do not this class spring bean and this class not in spring context
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;



    // this method will work when make user login request
    // trigger when we issue POST request to /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // read username and password via request
            UserLoginRequestModel userCredentials = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequestModel.class);

            // create a login token
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userCredentials.getEmail(), userCredentials.getPassword(), new ArrayList<>());

            // authenticate user
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Error when reading user credentials via request", e);
        }

    }

    // this method will work if user logs in successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // get authenticated user's principal
        User principal = (User) authResult.getPrincipal();
        String email = principal.getUsername();

        // TODO: JwtToken icin util yaz
        // create JWT Token
        String jwtToken = Jwts.builder()
                .setSubject(principal.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + ProjectConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, ProjectConstants.TOKEN_SECRET)
                .compact();

        // add token in response
        response.addHeader(ProjectConstants.AUTHORIZATION_HEADER_NAME, ProjectConstants.TOKEN_PREFIX + jwtToken);

        // get user id and add in response (optional in your case)
        UserDto userDto = userService.getUserWithEmail(email);
        response.addHeader(ProjectConstants.USER_ID_HEADER_NAME, userDto.getUserId());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
