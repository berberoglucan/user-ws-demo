package io.can.userwsdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.ui.request.UserLoginRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
// do not this class spring bean and this class not in spring context
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    // this method will work when make user login request
    // trigger when we issue POST request to /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // get authenticated user's principal and create jwt
        String token = jwtProvider.generateToken(authResult);

        // add token in response
        response.addHeader(ProjectConstants.AUTHORIZATION_HEADER_NAME, ProjectConstants.TOKEN_PREFIX + token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // TODO: auth basarisiz durumunda yapilacak alternatif bak
        System.out.println(failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
