package test;

import deploy.DeploymentConfiguration;
import entity.User;
import facades.UserFacade;
import java.security.spec.InvalidKeySpecException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        User user = facade.getUserByUserName("ole");
        assertEquals("ole", user.getUserName());
        assertEquals("User", user.getRoles().get(0));
    }
    
    @Test
    public void testCreateUserWithNonExistingUsername() throws Exception{
        User user = new User("ole","test");
        facade.createUser(user);
        assertEquals("ole", facade.getUserByUserName(user.getUserName()).getUserName());
        assertEquals(4, facade.getAllUsers().size());
        facade.deleteUser("ole");
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
        assertEquals("User", facade.authenticateUser("ole", "test").get(0));
    }
    
    @Test
    public void testGetAllUsers(){
        assertEquals(3, facade.getAllUsers().size());
    } 
}
