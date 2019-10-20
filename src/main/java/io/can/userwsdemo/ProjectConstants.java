package io.can.userwsdemo;

public class ProjectConstants {

    public static final int LENGTH_FOR_USER_ID = 30;

    // Security endpoints
    public static final String SIGN_UP_ENDPOINT = "/sign-up";
    public static final String LOGIN_ENDPOINT = "/login";

    // Security Constants
    public static final String TOKEN_PREFIX = "Bearer ";

    // Response header name constants
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private ProjectConstants() {}
}
