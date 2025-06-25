
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTest {
    
    public LoginTest() {
    }

    @Test
    public void testSetRegisteredUsername() {
    }

    @Test
    public void testSetRegisteredPassword() {
    }

    @Test
    public void testSetRegisteredPhoneNumber() {
    }

    @Test
    public void testSetLoginUsername() {
    }

    @Test
    public void testSetLoginPassword() {
    }

    @Test
    public void testSetName() {
    }

    @Test
    public void testSetSurname() {
    }

    @Test
    public void testGetRegisteredUsername() {
    }

    @Test
    public void testGetRegisteredPassword() {
    }

    @Test
    public void testGetRegisteredPhoneNumber() {
    }

    @Test
    public void testGetLoginUsername() {
    }

    @Test
    public void testGetLoginPassword() {
    }

    @Test
    public void testGetName() {
    }

    @Test
    public void testGetSurname() {
    }

    @Test
    public void testWelcomeInput() {
    }

    @Test
    public void testRegisterUser() {
    }

    @Test
    public void testCheckUserName() {
    }

    @Test
    public void testCheckPasswordComplexity() {
    }

    @Test
    public void testCheckCellPhoneNumber() {
    }

    @Test
    public void testAddCellPhoneNumber() {
    }

    @Test
    public void testReturnCellPhoneNumber() {
    }

    @Test
    public void testLoginUser() {
    }

    @Test
    public void testReturnLoginStatus() {
    }
    
    
    
        // Username Tests
    @Test
    void testUsernameCorrectlyFormatted_AssertEquals() {
        Login user = new Login();
        user.setRegisteredUsername("kyl_1");
        assertEquals(true, user.checkUserName(), "Username should be correctly formatted");
    }

    @Test
    void testUsernameIncorrectlyFormatted_AssertEquals() {
        Login user = new Login();
        user.setRegisteredUsername("kyle!!!!!!!");
        assertEquals(false, user.checkUserName(), "Username should be incorrectly formatted");
    }
    
        @Test
    void testUsernameCorrectlyFormatted_AssertTrue() {
        Login user = new Login();
        user.setRegisteredUsername("kyl_1");
        assertTrue(user.checkUserName(), "Username should be correctly formatted");
    }

    @Test
    void testUsernameIncorrectlyFormatted_AssertFalse() {
        Login user = new Login();
        user.setRegisteredUsername("kyle!!!!!!!");
        assertFalse(user.checkUserName(), "Username should be incorrectly formatted");
    }
    
    
        // Password Tests
    @Test
    void testPasswordMeetsRequirements_AssertEquals() {
        Login user = new Login();
        user.setRegisteredPassword("Ch&&sec@ke99!");
        assertEquals(true, user.checkPasswordComplexity(), "Password should meet complexity requirements");
    }

    @Test
    void testPasswordDoesNotMeetRequirements_AssertEquals() {
        Login user = new Login();
        user.setRegisteredPassword("password");
        assertEquals(false, user.checkPasswordComplexity(), "Password should not meet complexity requirements");
    }

    @Test
    void testPasswordMeetsRequirements_AssertTrue() {
        Login user = new Login();
        user.setRegisteredPassword("Ch&&sec@ke99!");
        assertTrue(user.checkPasswordComplexity(), "Password should meet complexity requirements");
    }

    @Test
    void testPasswordDoesNotMeetRequirements_AssertFalse() {
        Login user = new Login();
        user.setRegisteredPassword("password");
        assertFalse(user.checkPasswordComplexity(), "Password should not meet complexity requirements");
    }
    
    
    
    

    // Cell Number Tests
    @Test
    void testCellPhoneCorrectlyFormatted_AssertEquals() {
        Login user = new Login();
        user.setRegisteredPhoneNumber("+27838968976");
        assertEquals(true, user.checkCellPhoneNumber(), "Phone number should be correctly formatted");
    }

    @Test
    void testCellPhoneIncorrectlyFormatted_AssertEquals() {
        Login user = new Login();
        user.setRegisteredPhoneNumber("23345566");
        assertEquals(false, user.checkCellPhoneNumber(), "Phone number should be incorrectly formatted");
    }

    @Test
    void testCellPhoneCorrectlyFormatted_AssertTrue() {
        Login user = new Login();
        user.setRegisteredPhoneNumber("+27838968976");
        assertTrue(user.checkCellPhoneNumber(), "Phone number should be correctly formatted");
    }

    @Test
    void testCellPhoneIncorrectlyFormatted_AssertFalse() {
        Login user = new Login();
        user.setRegisteredPhoneNumber("23345566");
        assertFalse(user.checkCellPhoneNumber(), "Phone number should be incorrectly formatted");
    }
    
    
    
    

    // Login Tests
    @Test
    void testLoginSuccess() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(user.getLoginUsername().equals(user.getRegisteredUsername()) &&
                   user.getLoginPassword().equals(user.getRegisteredPassword()), 
                   "Login should be successful");
    }

    @Test
    void testLoginFail() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "wrongUser", "wrongPass", "+27838968976", "Kyle", "Smith");
        assertFalse(user.getLoginUsername().equals(user.getRegisteredUsername()) &&
                    user.getLoginPassword().equals(user.getRegisteredPassword()), 
                    "Login should fail");
    }

}

