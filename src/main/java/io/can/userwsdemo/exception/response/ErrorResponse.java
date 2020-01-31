package io.can.userwsdemo.exception.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private final String exceptionType;
    private final String message;
    private final String httpStatus;
    private final ZonedDateTime timeStamp;

}
