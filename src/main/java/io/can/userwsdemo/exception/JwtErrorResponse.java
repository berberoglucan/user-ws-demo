package io.can.userwsdemo.exception;

import lombok.Getter;
import lombok.ToString;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@ToString
public class JwtErrorResponse {

    // TODO: Ozellestirmek icin daha sonra gozden gecir
    private final String message;
    private final String httpStatus;
    private final ZonedDateTime timeStamp;

    public JwtErrorResponse(String message, String httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = ZonedDateTime.now(ZoneId.of("UTC"));
    }
}
