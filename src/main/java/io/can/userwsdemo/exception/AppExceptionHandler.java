package io.can.userwsdemo.exception;

import io.can.userwsdemo.exception.response.ErrorResponse;
import io.can.userwsdemo.security.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@ControllerAdvice
@Log4j2
public class AppExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException exc, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exceptionType(exc.getClass().getSimpleName())
                .message(exc.getMessage())
                .httpStatus(exc.getHttpStatus().toString())
                .timeStamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();

        writeLogWithRequestSpecific(exc, request);

        return new ResponseEntity<>(errorResponse, exc.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception exc, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exceptionType(exc.getClass().getSimpleName())
                .message(exc.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timeStamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();

        writeLogWithRequestSpecific(exc, request);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void writeLogWithRequestSpecific(Exception exc, WebRequest request) {
        String uri = request.getDescription(false).split("=")[1];
        StringBuilder sb = new StringBuilder();
        UsernamePasswordAuthenticationToken token = null;
        if (request.getUserPrincipal() != null) {
            sb.append("This user: ");
            token = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
            UserPrincipal principal = (UserPrincipal) token.getPrincipal();
            sb.append("UserId: ").append(principal.getUserId());
            sb.append(" - Username: ").append(principal.getUsername());
            sb.append(" - Authorities: ");
            String authorities = StringUtils.join(token.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new), ",");
            sb.append(authorities);
            WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
            sb.append(" - Ip Address: ").append(details.getRemoteAddress());
            sb.append("\nreceived an exception when trying to access this uri: ").append(uri);

        } else {
            sb.append("Exception when trying to access this uri: ").append(uri);
        }
        sb.append(" - Exception is : ").append(exc.getClass().getSimpleName()).append(" - ").append(exc.getMessage());
        log.error(sb.toString());
    }

}
