package com.zoinks.waterreporting.model;

/**
 * Created by sean on 2/21/17.
 * The four user types and their associated privilege levels.
 */

public enum UserType {
    USER (10), WORKER (20), MANAGER (30), ADMINISTRATOR (40);

    private int privilege;

    UserType(int privilege) {
        this.privilege = privilege;
    }

    /*
     * returns the privilege level as an int
     */
    public int getPrivilege() {
        return this.privilege;
    }
}
