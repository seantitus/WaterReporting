package com.zoinks.waterreporting;

import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.UserType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test for login
 * Created by Nancy on 03/21/2017.
 */

public class LoginTest {
    @Test
    public void nullUsername() {
        Facade facade = Facade.getInstance();
        facade.logout();
        assertEquals(facade.login(null, "irrelevantPassword"), false);
        assertEquals(facade.getCurrentUser(), null);
    }

    @Test
    public void nullPassword() {
        Facade facade = Facade.getInstance();
        facade.logout();
        assertEquals(facade.login("randomUser", null), false);
        assertEquals(facade.getCurrentUser(), null);
    }

    @Test
    public void nullUsernameAndPassword() {
        Facade facade = Facade.getInstance();
        facade.logout();
        assertEquals(facade.login(null, null), false);
        assertEquals(facade.getCurrentUser(), null);
    }

    @Test
    public void userDoesNotExist() {
        Facade facade = Facade.getInstance();
        facade.logout();
        assertEquals(facade.login("nonexistentUser", "irrelevantPassword"), false);
        assertEquals(facade.getCurrentUser(), null);
    }

    @Test
    public void userExistsWrongPassword() {
        Facade facade = Facade.getInstance();
        facade.logout();
        facade.registerUser("Test", "User", "testUser", "testPassword", UserType.USER);
        assertEquals(facade.login("testUser", "wrongPassword"), false);
        assertEquals(facade.getCurrentUser(), null);
    }

    @Test
    public void userExistsCorrectPassword() {
        Facade facade = Facade.getInstance();
        facade.logout();
        facade.registerUser("Test", "User", "testUser", "testPassword", UserType.USER);
        assertEquals(facade.login("testUser", "testPassword"), true);
        assertEquals(facade.getCurrentUser().getUsername(), "testUser");
    }
}
