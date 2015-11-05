package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("admin")
@RolesAllowed("Admin")
public class AllUsersRest {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade uf = new UserFacade();

    @Context
    private UriInfo context;

    public AllUsersRest() {
    }

    @GET
    @Path("/users")
    @Produces("application/json")
    public String getAllUsers() {
        List<User> list = uf.getAllUsers();
        return gson.toJson(list);
    }
    
    @PUT
    @Path("/user")
    public void deleteUser(String user) {
        User user1 = gson.fromJson(user, User.class);
        uf.deleteUser(user1.getUserName());
    }

}
