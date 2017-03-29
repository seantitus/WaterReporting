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
        assertEquals(facade.login(null, "irrelevantPassword"), false);
    }

    @Test
    public void nullPassword() {
        Facade facade = Facade.getInstance();
        assertEquals(facade.login("randomUser", null), false);
    }

    @Test
    public void nullUsernameAndPassword() {
        Facade facade = Facade.getInstance();
        assertEquals(facade.login(null, null), false);
    }

    @Test
    public void userDoesNotExist() {
        Facade facade = Facade.getInstance();
        assertEquals(facade.login("nonexistentUser", "irrelevantPassword"), false);
    }

    @Test
    public void userExistsWrongPassword() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser", "testPassword", UserType.USER);
        assertEquals(facade.login("testUser", "wrongPassword"), false);
    }

    @Test
    public void userExistsCorrectPassword() {
        Facade facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser", "testPassword", UserType.USER);
        assertEquals(facade.login("testUser", "wrongPassword"), false);
    }
}
