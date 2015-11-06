package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entity.User;
import facades.UserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("create")
public class CreateUserRest {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade uf = new UserFacade();

    @Context
    private UriInfo context;

    public CreateUserRest() {
    }

    /**
     * Creates a new user, add to db and return created user as Json-object. If
     * user exists return a json-Object with error-message.
     *
     * @param user json-string
     * @return json-Object with created user
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String user) {
        User u = gson.fromJson(user, User.class);
        u.AddRole("User");
        try {
            uf.createUser(u);
        } catch (Exception ex) {
            String error = "{\"message\": \"" + ex.getMessage() + "\"}";
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }
        return Response.status(Response.Status.CREATED).entity(gson.toJson(u)).build();
    }
}
