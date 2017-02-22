package com.zoinks.waterreporting.model;

/**
 * Created by Nancy on 02/13/2017.
 */

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType;

    /**
     * Create new user.
     * @param username The user's username.
     * @param password The user's password, should already be hashed.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param userType The user's type, used for privileges.
     */
    public User(String username, String password, String firstName, String lastName, UserType userType) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int checkPrivilege() {
        return userType.getPrivilege();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
