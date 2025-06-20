package validator;

import enity.User;

import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidatorTest {
    private UserValidator userValidator;
    private User testUser;
    private User user;
    @Before
    public void setUp() throws Exception{
        userValidator = UserValidator.getInstance();
        user = new User();
        testUser = new User();
        testUser.setName("оаоаоа");
        testUser.setId(1);
        testUser.setAge(13);
        testUser.setEmail("Yakaaaa");
        //?testUser.setBooks();
    }

    @Test
    public void validateTest(){
        boolean result = userValidator.validate(testUser);
        assertTrue(result);
    }

    @Test
    public void validateIfThereIsNoInformationAboutUserTest(){
        boolean result = userValidator.validate(user);
        assertFalse("О пользователе нет данных", result);
    }
}
