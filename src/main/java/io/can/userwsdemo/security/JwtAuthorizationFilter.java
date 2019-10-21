package io.can.userwsdemo.security;

import io.can.userwsdemo.enumeration.JwtClaimKey;
import io.can.userwsdemo.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.debug("Request uri: " + request.getRequestURI());
        String token = jwtProvider.resolveToken(request);

        if (StringUtils.hasText(token)) {
            // logic 1
            /* try {
                String email = (String) jwtProvider.getSpecificClaimFromJWT(token, JwtClaimKey.USER_EMAIL);
                UserDetails userDetails = userService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // added ip address and session id to authenticated user information
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (JwtException ex) {
                logger.error("Exception with this token: " + token + " Exception is ->" + ex.getMessage());
            }*/

            // logic 2
            String email = null;

            try {
                email = (String) jwtProvider.getSpecificClaimFromJWT(token, JwtClaimKey.USER_EMAIL);
                logger.debug("Token username value: " + email);
            } catch (JwtException ex) {
                logger.error("Exception with this token: " + token + " Exception is ->" + ex.getMessage());
            }

            if (StringUtils.hasText(email)) {

                UserDetails userDetails = userService.loadUserByUsername(email);
                logger.debug("User is : " + userDetails.getUsername());

                if (jwtProvider.isTokenValid(token, userDetails)) {
                    logger.debug(userDetails.getUsername() + "'s token is valid");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // added ip address and session id to authenticated user information
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

                logger.debug(userDetails.getUsername() + "'s token is not valid");
            }
        }
        chain.doFilter(request,response);
    }
}
