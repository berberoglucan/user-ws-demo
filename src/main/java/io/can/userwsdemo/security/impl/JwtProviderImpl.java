package io.can.userwsdemo.security.impl;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.config.AppProperties;
import io.can.userwsdemo.enumeration.JwtClaimKey;
import io.can.userwsdemo.security.JwtProvider;
import io.can.userwsdemo.security.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component("jwtProvider")
public class JwtProviderImpl implements JwtProvider {

    // custom application properties config file
    private final AppProperties appProperties;

    /**
     * This method generate new jwt token with authenticated user information
     * */
    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(now.getTime() + appProperties.getJwt().getTokenExpirationTime());

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimKey.USER_ID.getClaimKey(), userPrincipal.getUserId());
        claims.put(JwtClaimKey.USER_EMAIL.getClaimKey(), userPrincipal.getUsername());
        claims.put(JwtClaimKey.USER_AUTHORITIES.getClaimKey(), userPrincipal.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, appProperties.getJwt().getTokenSecretKey())
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader(ProjectConstants.AUTHORIZATION_HEADER_NAME);
        if (StringUtils.hasText(requestTokenHeader) && requestTokenHeader.startsWith(ProjectConstants.TOKEN_PREFIX)) {
            return requestTokenHeader.replace(ProjectConstants.TOKEN_PREFIX, "");
        }
        return null;
    }


    public Object getSpecificClaimFromJWT(String token, JwtClaimKey claimKey) {
        return getAllClaims(token).get(claimKey.getClaimKey());
    }

    public String getUserIdFromJWT(String token) {
        return (String) this.getSpecificClaimFromJWT(token, JwtClaimKey.USER_ID);
    }

    // Bu method baska yerlerde veya projelerde kullanilabilir
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userMail = (String) getSpecificClaimFromJWT(token, JwtClaimKey.USER_EMAIL);
        return userMail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ------------ private methods --------------

    private Claims getAllClaims(String token) {
       return this.getParserForJWT(token).getBody();
    }

    /**
     *  This method return parser from parse the JWT.
     *  Parser throws exception if there is an error related to JWT.
     *  Also this method validate the token
     * */
    private Jws<Claims> getParserForJWT(String token) {
        Jws<Claims> parser;
        try {
            parser = Jwts.parser()
                    .setSigningKey(appProperties.getJwt().getTokenSecretKey())
                    .parseClaimsJws(token);
            return parser;
        } catch (SignatureException | MalformedJwtException
                | ExpiredJwtException | UnsupportedJwtException
                | IllegalArgumentException ex){
            throw new JwtException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = this.getAllClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }

}
