package io.can.userwsdemo.enumeration;

public enum JwtClaimKey {

    USER_ID("userId"),
    USER_EMAIL("email"),
    USER_AUTHORITIES("authorities");

    private String claimKey;

    JwtClaimKey(String claimKey) {
        this.claimKey = claimKey;
    }

    public String getClaimKey() {
        return claimKey;
    }
}
