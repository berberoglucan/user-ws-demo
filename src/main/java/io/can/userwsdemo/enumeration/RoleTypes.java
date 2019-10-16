package io.can.userwsdemo.enumeration;

public enum RoleTypes {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String roleName;

    private RoleTypes(String roleName) {
        this.roleName = roleName;
    }

    public String getRole() {
        return this.roleName;
    }
}
