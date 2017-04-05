package com.zoinks.waterreporting;

import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.UserType;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterTest {
    @Test
    public void nullFirstName(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser(null, "lastName", "usernameONE", "password", UserType.USER), false);
    }

    @Test
    public void nullLastName(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", null, "usernameTWO", "password", UserType.USER), false);
    }

    @Test
    public void nullUsername(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", null, "password", UserType.USER), false);
    }

    @Test
    public void nullPassword(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", "usernameTHREE", null, UserType.USER), false);
    }

    @Test
    public void nullUserType(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", "usernameFOUR", "password", null), false);
    }

    @Test
    public void nullAllFields(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser(null, null, null, null, null), false);
    }

    @Test
    public void takenUsername(){
        Facade facade = Facade.getInstance();
        facade.registerUser("firstName", "lastName", "originalUsername", "password", UserType.USER);
        assertEquals(facade.registerUser("firstName", "lastName", "originalUsername", "password", UserType.USER), false);
    }

    @Test
    public void registerSuccessfully(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", "usernameFIVE", "password", UserType.USER), true);
    }
}