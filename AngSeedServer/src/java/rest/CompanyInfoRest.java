package rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RolesAllowed("User")
@Path("search")
public class CompanyInfoRest {

    @Context
    private UriInfo context;

    public CompanyInfoRest() {
    }

    /**
     * Returns json with searched company
     * @param option 
     * @param searchText
     * @param country
     * @return
     * @throws MalformedURLException
     * @throws IOException 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{option}/{searchText}/{country}")
    public Response searchForCompany(@PathParam("option") String option, @PathParam("searchText") String searchText, @PathParam("country") String country) throws MalformedURLException, IOException {
        String jsonStr = null;
        HttpURLConnection con = null;
        try {
            String urlToUse = "http://cvrapi.dk/api?" + option + "=" + searchText + "&country=" + country;
            URL url = new URL(urlToUse);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            if (con.getResponseCode() != 200) {
                String error = "{\"error\":\"" + con.getResponseMessage() + "\", \"message\":\"The Company you searched for does not exist with the given " + option + "\"}";
                return Response.status(con.getResponseCode()).entity(error).build();
            }
            Scanner scan = new Scanner(con.getInputStream());
            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
            System.out.println("json " + jsonStr);
            scan.close();
            return Response.status(con.getResponseCode()).entity(jsonStr).build();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CompanyInfoRest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompanyInfoRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
