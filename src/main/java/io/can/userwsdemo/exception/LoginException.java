package io.can.userwsdemo.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends ApplicationException {

    private static final long serialVersionUID = -7782902163171959478L;

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public LoginException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
