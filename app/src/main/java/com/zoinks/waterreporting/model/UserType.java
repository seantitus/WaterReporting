package com.zoinks.waterreporting.model;

/**
 * Created by sean on 2/21/17.
 * The four user types and their associated privilege levels.
 */

public enum UserType {
    User (10),
    Worker (20),
    Manager (30),
    Administrator (40);
    private int privilege;

    UserType(int privilege) {
        this.privilege = privilege;
    }
    public int getPrivilege() {
        return this.privilege;
    }
}
