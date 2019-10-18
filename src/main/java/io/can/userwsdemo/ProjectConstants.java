package io.can.userwsdemo;

public class ProjectConstants {

    public static final int LENGTH_FOR_USER_ID = 30;

    // Security endpoints
    public static final String SIGN_UP_ENDPOINT = "/sign-up";
    public static final String LOGIN_ENDPOINT = "/login";

    // Security Constants
    public static final long EXPIRATION_TIME = 864000000L; // 10 days (milliseconds) for token expired
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0"; // encryption of the value in the access token

    // Response header name constants
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String USER_ID_HEADER_NAME = "userId";

    private ProjectConstants() {}
}
