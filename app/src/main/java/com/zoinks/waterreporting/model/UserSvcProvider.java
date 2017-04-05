package com.zoinks.waterreporting.model;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage information and services related to users
 * Has package visibility because it should only be accessed from the facade, not directly
 *
 * Created by Nancy on 02/21/2017.
 */

class UserSvcProvider {
    private User currentUser;
    private final Map<String, User> USERS = new HashMap<>();

    /**
     * Returns the currently logged in user, if any
     *
     * @return the currently logged in user, if any
     */
    User getCurrentUser() {
        return currentUser;
    }

    /**
     * Attempts to register a new user
     *
     * @param firstName first name of the new user
     * @param lastName last name of the new user
     * @param username username of the new user
     * @param password un-hashed password of new user
     * @param privilege type of new user, used for privilege
     * @return True if the new user was registered, False if the user was not registered (ie username taken)
     */
    boolean register(String firstName, String lastName, String username, String password,
                            UserType privilege) {
        if (USERS.containsKey(username)) {
            return false;
        } else {
            User user = new User(username, SHA1(password), firstName, lastName, privilege);
            USERS.put(username, user);
            return true;
        }
    }

    /**
     * Updates attributes passed in to update profile of current user
     *
     * @param oldUsername username from current user
     * @param newUsername username from edit text
     * @param firstName new or old firstName
     * @param lastName new or old lastName
     * @param password "" if password was unchanged, otherwise old hashed password
     * @return boolean true if profile was updated
     */
    boolean update(String oldUsername, String newUsername, String firstName, String lastName,
                          String password, String email, String address, String phone) {
        if (!newUsername.equals(oldUsername)) {
            if (USERS.containsKey(newUsername)) {  // trying to change to a username that's taken
                return false;
            } else {
                User updatedUser;
                if (password.length() > 0) {
                    // changing password
                    updatedUser = new User(newUsername, SHA1(password), firstName, lastName,
                            UserType.values()[USERS.get(oldUsername).checkPrivilege() / 10 - 1]);
                } else {
                    // keeping same password
                    updatedUser = new User(newUsername, password, firstName, lastName,
                            UserType.values()[USERS.get(oldUsername).checkPrivilege() / 10 - 1]);
                }
                updatedUser.setEmail(email);
                updatedUser.setAddress(address);
                updatedUser.setPhone(phone);
                USERS.put(newUsername, updatedUser);
                USERS.remove(oldUsername);
                currentUser = updatedUser;
                return true;
            }
        } else {
            User user = USERS.get(newUsername);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            if (password.length() > 0) {
                user.setPassword(SHA1(password));
            }
            user.setEmail(email);
            user.setAddress(address);
            user.setPhone(phone);
            USERS.put(newUsername, user);
            currentUser = user;
            return true;
        }
    }

    /**
     * Attempts login; data is checked in the UI for null/etc before being passed here
     *
     * @param username username of profile trying to login as
     * @param password to be checked for a match
     * @return True only if user exists and password matches
     *         False if password incorrect, user does not exist, or user blocked
     */
    boolean login(String username, String password) {
        if (username == null || password == null) {  // required bc null is a valid key in hashmaps
            return false;
        }

        if (USERS.containsKey(username)) {
            User user = USERS.get(username);
            if (user.getPassword().equals(SHA1(password))) {
                if (user.isLockedOut()) {
                    return false;
                } else {
                    currentUser = user;
                    user.clearIncorrectLoginAttempts();
                    return true;
                }
            } else {
                user.incrementIncorrectLoginAttempts();
            }
        }
        return false;
    }

    /**
     * Logs user out of application and resets current user
     */
    void logout() {
        currentUser = null;
    }

    /**
     * SHA1 hashes password
     *
     * @param password  to be hashed
     * @return SHA1 hash of password
     */
    private String SHA1(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] textBytes = password.getBytes("iso-8859-1");
            md.update(textBytes, 0, textBytes.length);
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Exception e) {
            return "";
        }
    }

    private String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfByte = (b >>> 4) & 0x0F;
            int two_halves = 0;
            do {
                buf.append((0 <= halfByte) && (halfByte <= 9) ? (char) ('0' + halfByte) : (char) ('a' + (halfByte - 10)));
                halfByte = b & 0x0F;
            } while (two_halves++ < 1);
        }
        return buf.toString();
    }
}
