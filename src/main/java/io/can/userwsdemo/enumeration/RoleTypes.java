package io.can.userwsdemo.enumeration;

public enum RoleTypes {

    ADMIN("ADMIN"),
    USER("USER");

    private String roleName;

    private RoleTypes(String roleName) {
        this.roleName = roleName;
    }

    public String getRole() {
        return this.roleName;
    }
}
