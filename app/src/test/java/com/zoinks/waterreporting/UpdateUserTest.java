package com.zoinks.waterreporting;

import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.User;
import com.zoinks.waterreporting.model.UserType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for updateUser()
 * Created by sean on 4/5/17.
 */

public class UpdateUserTest {
    @Test
    public void usernameTaken() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.registerUser("Test1", "User1", "testUser1", "testPassword1", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), "testUser1", user.getFirstName(), user.getLastName(),
                "", user.getEmail(), user.getAddress(), user.getPhone()), false);
        user = facade.getCurrentUser();
        assertEquals(user.getUsername().equals("testUser3"), true);
    }
    @Test
    public void changeUsername() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), "testUser2", user.getFirstName(), user.getLastName(),
                "", user.getEmail(), user.getAddress(), user.getPhone()), true);
        user = facade.getCurrentUser();
        assertEquals(user.getUsername().equals("testUser2"), true);
    }
    @Test
    public void changeFirstName() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), user.getUsername(), "firstName", user.getLastName(),
                "", user.getEmail(), user.getAddress(), user.getPhone()), true);
        user = facade.getCurrentUser();
        assertEquals(user.getFirstName().equals("firstName"), true);
    }
    @Test
    public void changeLastName() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), user.getUsername(), user.getFirstName(),
                "lastName", "", user.getEmail(), user.getAddress(), user.getPhone()), true);
        user = facade.getCurrentUser();
        assertEquals(user.getLastName().equals("lastName"), true);
    }
    @Test
    public void changeEmail() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), user.getUsername(), user.getFirstName(),
                user.getLastName(), "", "emailAddress", user.getAddress(), user.getPhone()), true);
        user = facade.getCurrentUser();
        assertEquals(user.getEmail().equals("emailAddress"), true);
    }
    @Test
    public void changeAddress() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), user.getUsername(), user.getFirstName(),
                user.getLastName(), "", user.getEmail(), "newAddress", user.getPhone()), true);
        user = facade.getCurrentUser();
        assertEquals(user.getAddress().equals("newAddress"), true);
    }
    @Test
    public void changePhone() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), user.getUsername(), user.getFirstName(),
                user.getLastName(), "", user.getEmail(), user.getAddress(), "12345"), true);
        user = facade.getCurrentUser();
        assertEquals(user.getPhone().equals("12345"), true);
    }
    @Test
    public void changePassword() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser3", "testPassword", UserType.USER);
        facade.login("testUser3", "testPassword");
        User user = facade.getCurrentUser();
        assertEquals(facade.updateUser(user.getUsername(), user.getUsername(), user.getFirstName(),
                user.getLastName(), "newPass", user.getEmail(), user.getAddress(), user.getPhone()), true);
        facade.logout();
        assertEquals(facade.login("testUser3", "newPass"), true);
    }

}
