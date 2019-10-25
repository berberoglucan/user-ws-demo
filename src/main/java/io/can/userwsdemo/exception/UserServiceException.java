package io.can.userwsdemo.exception;

import org.springframework.http.HttpStatus;


/**
 *    This exception class for User service layer
 * */
public class UserServiceException extends ApplicationException {

    private static final long serialVersionUID = -4570251944002856583L;

    public UserServiceException() {
    }

    public UserServiceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public UserServiceException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
