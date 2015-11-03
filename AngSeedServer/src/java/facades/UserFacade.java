package facades;

import entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFacade {
  
  private final  Map<String, User> users = new HashMap<>();

  public UserFacade() {
    //Test Users
    User user = new User("user","test");
    user.AddRole("User");
    users.put(user.getUserName(),user );
    User admin = new User("admin","test");
    admin.AddRole("Admin");
    users.put(admin.getUserName(),admin);
    
    User both = new User("user_admin","test");
    both.AddRole("User");
    both.AddRole("Admin");
    users.put(both.getUserName(),both );
  }
  
  public User getUserByUserId(String id){
    return users.get(id);
  }
  /*
  Return the Roles if users could be authenticated, otherwise null
  */
  public List<String> authenticateUser(String userName, String password){
    User user = users.get(userName);
    return user != null && user.getPassword().equals(password) ? user.getRoles(): null;
  }
  
}
