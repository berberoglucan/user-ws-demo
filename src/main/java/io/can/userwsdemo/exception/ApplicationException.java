package io.can.userwsdemo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -6491375561367180199L;

    @Getter
    private HttpStatus httpStatus;


    public ApplicationException() {
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
