package io.can.userwsdemo.enumeration;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessages {

    // TODO: ihtiyaca gore ozellestilebilir

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields."),
    RECORD_ALREADY_EXISTS("Record already exists."),
    INTERNAL_SERVER_ERROR("Internal server error."),
    NO_RECORD_FOUND("Record is not found."),
    AUTHENTICATION_FAILED("Authentication failed."),
    COULD_NOT_UPDATE_RECORD("Could not update record."),
    COULD_NOT_DELETE_RECORD("Could not delete record."),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified."),
    INVALID_ID("Id is invalid. Like a null or not numeric etc.");

    @Getter
    @Setter
    private String errorMessage;

    public String withGiven(String given) {
        return this.getErrorMessage() + " With this given: " + given;
    }
}
