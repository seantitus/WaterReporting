package com.zoinks.waterreporting.model;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Gets a list of the string values of the user types (for spinner population)
     *
     * @return a list of the string values of the user types (for spinner population)
     */
    public static List<String> getValues() {
        List<String> list = new ArrayList<>();
        list.add("USER");
        list.add("WORKER");
        list.add("MANAGER");
        list.add("ADMINISTRATOR");
        return list;
    }

    /**
     * Gets the UserType associated with a certain privilege level
     *
     * @param privilege the privilege level for which to get the UserType
     * @return the UserType associated with a certain privilege level
     */
    public static UserType get(int privilege) {
        switch (privilege) {
            case 10:
                return USER;
            case 20:
                return WORKER;
            case 30:
                return MANAGER;
            case 40:
                return ADMINISTRATOR;
            default:
                return null;
        }
    }
}
