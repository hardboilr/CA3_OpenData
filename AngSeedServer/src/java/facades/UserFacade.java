package facades;

import deploy.DeploymentConfiguration;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import security.PasswordHash;

public class UserFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);;

    public UserFacade() {
        try {
            //Test Users
            User user = new User("user", PasswordHash.createHash("test"));
            User admin = new User("admin", PasswordHash.createHash("test"));
            User both = new User("user_admin", PasswordHash.createHash("test"));
            user.AddRole("User");
            admin.AddRole("Admin");
            both.AddRole("User");
            both.AddRole("Admin");
            createUser(user);
            createUser(admin);
            createUser(both);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    public User getUserByUserName(String userName) throws Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByUserName", User.class).setParameter("userName", userName);
            List<User> users = query.getResultList();
            if (!users.isEmpty()) {
                return users.get(0);
            } else {
                throw new Exception("No user found with user-name: " + userName);
            }
        } finally {
            em.close();
        }
    }

    public User createUser(User u) throws Exception {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, u.getUserName());
        if (user != null) {
            throw new Exception("User with user-name: " + u.getUserName() + " already exists");
        } else {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }
        return u;
    }

    /*
     Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        EntityManager em = getEntityManager();
        User user = new User();
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByUserName", User.class).setParameter("userName", userName);
            List<User> users = query.getResultList();
            if (!users.isEmpty()) {
                user = users.get(0);
            } else {
                throw new Exception("No user found with user-name: " + userName);
            }
        } finally {
            em.close();
        }
        if (PasswordHash.validatePassword(password, user.getPassword()) == true) {
            return user.getRoles();
        } else {
            return null;
        }
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
