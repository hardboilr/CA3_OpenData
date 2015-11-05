package rest;

import com.google.gson.Gson;
import deploy.DeploymentConfiguration;
import entity.DailyRate;
import facades.CurrencyFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@RolesAllowed("User")
@Path("currency/dailyrates")
public class CurrencyRest {

    @Context
    private UriInfo context;
    private CurrencyFacade ctrl = new CurrencyFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));
    private Gson gson = new Gson();

    public CurrencyRest() {
    }

    @GET
    @Produces("application/json")
    public Response getDailyRates() {
        List<DailyRate> rates = ctrl.getDailyRates();
        return Response.ok(gson.toJson(rates)).build();
    }
}
