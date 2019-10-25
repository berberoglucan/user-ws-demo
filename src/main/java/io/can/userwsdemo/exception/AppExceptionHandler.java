package io.can.userwsdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException exc) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exceptionType(exc.getClass().getSimpleName())
                .message(exc.getMessage())
                .httpStatus(exc.getHttpStatus().toString())
                .timeStamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();

        return new ResponseEntity<>(errorResponse, exc.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception exc) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exceptionType(exc.getClass().getSimpleName())
                .message(exc.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timeStamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
