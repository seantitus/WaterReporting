package com.zoinks.waterreporting;

import com.zoinks.waterreporting.model.UserSvcProvider;
import com.zoinks.waterreporting.model.UserType;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for login
 * Created by Nancy on 03/21/2017.
 */

public class LoginTest {

    @Test
    public void userDoesNotExist() {
        UserSvcProvider usp = UserSvcProvider.getInstance();
        assertEquals(usp.login("nonexistentUser", "irrelevantPassword"), false);
    }

    @Test
    public void userExistsWrongPassword() {
        UserSvcProvider usp = UserSvcProvider.getInstance();
        usp.register("Test", "User", "testUser", "testPassword", UserType.USER);
        assertEquals(usp.login("testUser", "wrongPassword"), false);
    }

    @Test
    public void userExistsCorrectPassword() {
        UserSvcProvider usp = UserSvcProvider.getInstance();
        usp.register("Test", "User", "testUser", "testPassword", UserType.USER);
        assertEquals(usp.login("testUser", "testPassword"), true);
    }
}
