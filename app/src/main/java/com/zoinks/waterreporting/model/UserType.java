package com.zoinks.waterreporting.model;

/**
 * The four user types and their associated privilege levels.
 *
 * Created by sean on 2/21/17.
 */

public enum UserType {
    USER (10), WORKER (20), MANAGER (30), ADMINISTRATOR (40);

    private final int privilege;

    UserType(int privilege) {
        this.privilege = privilege;
    }

    /**
     * Returns the integer representation of privilege level associated with a user type
     *
     * @return integer representation of privilege level associated with a user type
     */
    public int getPrivilege() {
        return this.privilege;
    }
}
