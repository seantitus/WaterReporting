package com.zoinks.waterreporting.model;

import android.util.Log;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage information and services related to users
 * Implements Singleton design pattern
 *
 * Created by Nancy on 02/21/2017.
 */

public class UserSvcProvider {
    private static UserSvcProvider usp;
    private User currentUser;
    private static final Map<String, User> USERS = new HashMap<>();

    private UserSvcProvider() {
        USERS.put("user", new User("user", SHA1("user"), "User", "User", UserType.USER));
        USERS.put("worker", new User("worker", SHA1("worker"), "Worker", "User", UserType.WORKER));
        USERS.put("manager", new User("manager", SHA1("manager"), "Manager", "User", UserType.MANAGER));
        USERS.put("admin", new User("admin", SHA1("admin"), "Admin", "User", UserType.ADMINISTRATOR));
    }

    public static UserSvcProvider getInstance() {
        if (usp == null) {
            usp = new UserSvcProvider();
        }
        return usp;
    }

    /**
     * Returns the currently logged in user, if any
     *
     * @return the currently logged in user, if any
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Attempts to register a new user
     *
     * @param firstName first name of the new user
     * @param lastName last name of the new user
     * @param username username of the new user
     * @param password unhashed password of new user
     * @param privilege type of new user, used for privilege
     * @return True if the new user was registered, False if the user was not registered (ie username taken)
     */
    public boolean register(String firstName, String lastName, String username, String password,
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
     * Updates attributes passed in
     * @param firstName new or old firstName
     * @param lastName new or old lastName
     * @param username username from edit text
     * @param oldUsername username from current user
     * @param password new or old password
     * @return boolean true if profile was updated
     */
    public boolean update(String firstName, String lastName, String username, String oldUsername, String password) {
        if (!username.equals(oldUsername)) {
            if (USERS.containsKey(username)) {
                return false;
            } else {
                User updatedUser = new User(username, SHA1(password), firstName, lastName,
                        UserType.values()[USERS.get(oldUsername).checkPrivilege() / 10 - 1]);
                USERS.put(username, updatedUser);
                USERS.remove(oldUsername);
                return true;
            }
        } else {
            User user = USERS.get(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(SHA1(password));
            USERS.put(username, user);
            currentUser = user;
            Log.d("Current User: ", currentUser.getFirstName());
            return true;
        }
    }

    /**
     * Attempts login
     *
     * @param username username of profile trying to login as
     * @param password to be checked for a match
     * @return True only if user exists and password matches
     *         False if password incorrect, user does not exist, user blocked
     */
    public boolean login(String username, String password) {
        if (USERS.containsKey(username)) {
            User user = USERS.get(username);
            if (user.getPassword().equals(SHA1(password))) {  //TODO: and if user is not banned, etc
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * Logs user out of application and resets current user
     */
    public void logout() {
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
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}