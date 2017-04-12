package com.zoinks.waterreporting.model;

/**
 * Class to hold information about users
 *
 * Created by Nancy on 02/13/2017.
 */

public class User {
    // required information
    private final String username;
    private String password;
    private String firstName;
    private String lastName;
    private final UserType userType;
    private int incorrectLoginAttempts;

    // optional profile information
    private String email;
    private String address;
    private String phone;

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
        this.incorrectLoginAttempts = 0;
    }

    @Override
    public String toString() {
        return username + " / " + firstName + " " + lastName + " / " + userType;
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

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int checkPrivilege() {
        return userType.getPrivilege();
    }

    public boolean isLockedOut() {
        return incorrectLoginAttempts >= 3;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setAddress(String address) {
        this.address = address;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    public void unblockAccount() {
        incorrectLoginAttempts = 0;
    }

    public void incrementIncorrectLoginAttempts() {
        incorrectLoginAttempts++;
    }
}
