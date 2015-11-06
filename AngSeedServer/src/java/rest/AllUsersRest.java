package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("admin")
@RolesAllowed("Admin")
public class AllUsersRest {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade ctrl = new UserFacade();

    @Context
    private UriInfo context;

    public AllUsersRest() {
    }

    /**
     * Gets all users from db and returns them as a jsonArray.
     * @return jsonArray  
     */
    @GET
    @Path("/users")
    @Produces("application/json")
    public Response getAllUsers() {
        List<User> list = ctrl.getAllUsers();
        return Response.status(Response.Status.OK).entity(gson.toJson(list)).build();
    }

    /**
     * Deletes a user from db and returns deleted user as jsonObject
     * @param user Json-string
     * @return jsonObject with deleted user
     */
    @PUT
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(String user) {
        User user1 = gson.fromJson(user, User.class);
        ctrl.deleteUser(user1.getUserName());
        return Response.status(Response.Status.OK).entity(gson.toJson(user1)).build();
    }
}
