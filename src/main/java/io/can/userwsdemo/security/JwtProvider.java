package io.can.userwsdemo.security;

import io.can.userwsdemo.enumeration.JwtClaimKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {

    /**
     * This method generate new jwt token with authenticated user information
     */
    String generateToken(Authentication authentication);

    /**
     * This method resolves jwt token from request header
     * If request Auhorization header not includes the Bearer, this method returns null
     */
    String resolveToken(HttpServletRequest request);

    /**
     * This method give specific claim from JWT with JwtClaimKey type
     */
    Object getSpecificClaimFromJWT(String token, JwtClaimKey claimKey);

    /**
     * This method give user id from JWT
     */
    String getUserIdFromJWT(String token);

    /**
     * This method validate token as token is expired and match user email from database
     */
    boolean isTokenValid(String token, UserDetails userDetails);

}
