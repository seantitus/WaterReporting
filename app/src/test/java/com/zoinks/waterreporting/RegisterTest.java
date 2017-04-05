package com.zoinks.waterreporting;

import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.UserType

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
        assertEquals(facade.registerUser(null, "lastName", "username", "password", UserType.USER), false);
    }

    public void nullLastName(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", null, "username", "password", UserType.USER), false);
    }

    public void nullUsername(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", null, "password", UserType.USER), false);
    }

    public void nullPassword(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", "username", null, UserType.USER), false);
    }

    public void nullUserType(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", "username", "password", null), false);
    }

    public void nullAllFields(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser(null, null, null, null, null), false);
    }

    public void takenUsername(){
        Facade facade = Facade.getInstance();
        facade.registerUser("firstName", "lastName", "originalUsername", "password", UserType.USER);
        assertEquals(facade.registerUser("firstName", "lastName", "originalUsername", "password", UserType.USER), false);
    }

    public void registerSuccessfully(){
        Facade facade = Facade.getInstance();
        assertEquals(facade.registerUser("firstName", "lastName", "username", "password", UserType.USER), true);
    }
}