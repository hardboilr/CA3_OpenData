/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import entity.User;
import facades.UserFacade;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sebastiannielsen
 */
public class UserFacadeTest {
    
    private UserFacade facade;
    public UserFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DeploymentConfiguration.setTestModeOn();
    }
    
    @Before
    public void setUp() {
        facade = new UserFacade();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetUserByValidUserName() throws Exception{
        User user = facade.getUserByUserName("user");
        assertEquals("user", user.getUserName());
        assertEquals("User", user.getRoles().get(0));
    }
    
    @Test(expected = Exception.class)
    public void testGetUserByInvalidUserName() throws Exception{
        User user = facade.getUserByUserName("Sebastian");
        assertEquals("Sebastian", user.getUserName());
        assertEquals("User", user.getRoles().get(0));
    }
    
    @Test
    public void testCreateUserWithNonExistingUsername() throws Exception{
        User user = new User("Sebastian","Test");
        facade.createUser(user);
        assertEquals("Sebastian", facade.getUserByUserName(user.getUserName()).getUserName());
        assertEquals(4, facade.getAllUsers().size());
        facade.deleteUser("Sebastian");
    }
    
    @Test(expected = Exception.class)
    public void testCreateUserWithExistingUsername() throws Exception{
        User user = new User("user","test");
        facade.createUser(user);
        assertEquals("user", facade.getUserByUserName(user.getUserName()).getUserName());
        assertEquals(4, facade.getAllUsers().size());
        facade.deleteUser("user");
    }
    
    @Test
    public void testAuthenticateUserWithValidUsername() throws InvalidKeySpecException, Exception{
        assertEquals("User", facade.authenticateUser("user", "test").get(0));
    }
    
    @Test(expected = Exception.class)
    public void testAuthenticateUserWithInvalidUsername() throws InvalidKeySpecException, Exception{
        assertEquals("User", facade.authenticateUser("Sebastian", "test").get(0));
    }
    
    @Test
    public void testGetAllUsers(){
        assertEquals(3, facade.getAllUsers().size());
    } 
}
