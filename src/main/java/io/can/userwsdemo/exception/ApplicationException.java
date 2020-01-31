package io.can.userwsdemo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *  This class is main exception class. New exception class must extend this exception class.
 * */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -6491375561367180199L;

    @Getter
    private HttpStatus httpStatus;


    public ApplicationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApplicationException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
    }

}
