package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;
import facades.UserFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("create")
public class CreateUserRest {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UserFacade uf = new UserFacade();

    @Context
    private UriInfo context;

    public CreateUserRest() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(String user) throws Exception {
        User u = gson.fromJson(user, User.class);
        u.AddRole("user");
        uf.createUser(u);
    }

}
