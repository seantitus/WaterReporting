package com.zoinks.waterreporting.model;

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
        USERS.put("admin", new User("admin", "d033e22ae348aeb5660fc2140aec35850c4da997"));
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

    public boolean register(String username, String password) {
        if (USERS.containsKey(username)) {
            return false;
        } else {
            User user = new User(username, SHA1(password));
            USERS.put(username, user);
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
