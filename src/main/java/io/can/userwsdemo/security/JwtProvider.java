package io.can.userwsdemo.security;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.enumeration.JwtClaimKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("jwtProvider")
public class JwtProvider {


    /**
     * This method generate new jwt token with authenticated user information
     * */
    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(now.getTime() + ProjectConstants.EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimKey.USER_ID.getClaimKey(), userPrincipal.getUserId());
        claims.put(JwtClaimKey.USER_EMAIL.getClaimKey(), userPrincipal.getUsername());
        claims.put(JwtClaimKey.USER_AUTHORITIES.getClaimKey(), userPrincipal.getAuthorities());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, ProjectConstants.TOKEN_SECRET)
                .compact();
    }


}
