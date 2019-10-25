package io.can.userwsdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.can.userwsdemo.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized deneme");

        /* HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exceptionType(authException.getClass().getSimpleName())
                .message(authException.getMessage() + " " + request.getRequestURI())
                .httpStatus(unauthorized.toString())
                .timeStamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();

        response.setContentType("application/json");
        response.setStatus(unauthorized.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse)); */
    }
}
