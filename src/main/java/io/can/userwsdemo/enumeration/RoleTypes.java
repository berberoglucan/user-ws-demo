package io.can.userwsdemo.enumeration;

public enum RoleTypes {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String roleName;

    private RoleTypes(String roleName) {
        this.roleName = roleName;
    }

    public String getRole() {
        return this.roleName;
    }
}
