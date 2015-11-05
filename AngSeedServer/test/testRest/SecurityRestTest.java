package testRest;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import entity.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApplicationConfig;

public class SecurityRestTest {

    static Server server;

    public SecurityRestTest() {
        baseURI = "http://localhost:8082";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8082);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }

    /**
     * Try to log in with wrong username
     */
    @Test
    public void LoginWrongUsername() {
        given().
                contentType("application/json").
                body("{'username':'john','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(404).
                body("error.message", equalTo("Invalid username or password"));
    }

    /**
     * Try to log in with both wrong username and password
     */
    @Test
    public void LoginWrongUsernameAndPassword() {
        //wrong username and password
        given().
                contentType("application/json").
                body("{'username':'john','password':'doe'}").
                when().
                post("/login").
                then().
                statusCode(404).
                body("error.message", equalTo("Invalid username or password"));
    }

    /**
     * Log in as user
     */
    @Test
    public void Login() {
        //Successful login
        given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200);
    }

    /**
     * Expect 401 Unauthorized on all because not logged in
     */
    @Test
    public void testEndPointsNotLoggedIn() {
        //code 401: Unauthorized
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                when().
                get("/admin/users").
                then().
                statusCode(401);
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                body(new User()).
                when().
                put("/admin/user").
                then().
                statusCode(401);
        given(). //CurrencyRest @RolesAllowed("User")
                contentType("application/json").
                when().
                get("/currency/dailyrates").
                then().
                statusCode(401);
    }

    /*
     USER: 
     Expect 401 on endpoints "/admin/users" + "/admin/user" because NOT ALLOWED
     Expect 200 on endpoints "/currency/dailyrates" because ALLOWED
     */
    @Test
    public void testEndPointsUser() {
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'user','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();

        given(). //CurrencyRest @RolesAllowed("User")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/currency/dailyrates").
                then().
                statusCode(200);
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/admin/users").
                then().
                statusCode(403).
                body("error.message", equalTo("You are not authorized to perform the requested operation"));
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                body(new User()).
                when().
                put("/admin/user").
                then().
                statusCode(403).
                body("error.message", equalTo("You are not authorized to perform the requested operation"));
    }
    
    /*
     ADMIN: 
     Expect 200 on endpoints "/admin/users" + "/admin/user" because ALLOWED
     Expect 401 on endpoints "/currency/dailyrates" because NOT ALLOWED
     */

    @Test
    public void testEndPointsAdmin() {
        User user = new User("hans", "1234");
        //Create a user we can delete later
        given().contentType("application/json").
                body(user).
                when().
                post("/create").
                then().
                statusCode(201).
                body("userName", equalTo("hans")).
                body("roles", hasItems("User"));
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given(). //CurrencyRest @RolesAllowed("User")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/currency/dailyrates").
                then().
                statusCode(403).
                body("error.message", equalTo("You are not authorized to perform the requested operation"));
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/admin/users").
                then().
                statusCode(200);
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                body(user).
                when().
                put("/admin/user").
                then().
                statusCode(200);
    }
}
