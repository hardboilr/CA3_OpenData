package facades;

import deploy.DeploymentConfiguration;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import security.PasswordHash;

public class UserFacade {

    private EntityManagerFactory emf;

    public UserFacade(){
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    }

    public User getUserByUserName(String userName) throws Exception {
        EntityManager em = getEntityManager();
        try {
            User user = em.find(User.class, userName);
            return user;
        } catch (NoResultException ex){
                throw new Exception("No user found with user-name: " + userName);
            }
         finally {
            em.close();
        }
    }

    public User createUser(User u) throws Exception {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, u.getUserName());
        if (user != null) {
            throw new Exception("User with user-name: " + u.getUserName() + " already exists");
        } else {
            u.setPassword(PasswordHash.createHash(u.getPassword()));
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
        User user;
        try {
            user = em.find(User.class, userName);
            if (user == null) {
                throw new Exception("No user found with user-name: " + userName);
            }
            if (PasswordHash.validatePassword(password, user.getPassword())) {
                return user.getRoles();
            } else {
                return null;
            }
        } finally {
            em.close();
        }

    }
    public List<User> getAllUsers() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    public void deleteUser(String userName) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userName);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
