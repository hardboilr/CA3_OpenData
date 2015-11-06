package testRest;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import deploy.DeploymentConfiguration;
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

public class AllUsersRestTest {

    static Server server;
    
    public AllUsersRestTest() {
        DeploymentConfiguration.setTestModeOn();
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
     * Login as admin at get all users. Test that our response-body has all
     * three users from db
     */
    @Test
    public void TestGetAllUsers() {
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/admin/users").
                then().
                statusCode(200).
                body("userName", hasItems("admin", "user", "user_admin")).
                body("[0].roles", hasItems("Admin"));
    }

    /**
     * Create a test-user. Login as admin. Get all users and check that we have
     * 4 users. Delete the test-user and then test that 3 are left.
     */
    @Test
    public void TestDeleteUser() {
        User user = new User("tobias", "1234");
        //Create a user we can delete later
        given().contentType("application/json").
                body(user).
                when().
                post("/create").
                then().
                statusCode(201).
                body("userName", equalTo("tobias")).
                body("roles", hasItems("User"));
        //First, make a login to get the token for the Authorization, saving the response body in String json
        String json = given().
                contentType("application/json").
                body("{'username':'admin','password':'test'}").
                when().
                post("/login").
                then().
                statusCode(200).extract().asString();
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/admin/users").
                then().
                statusCode(200).
                body("size()", equalTo(4));
        given(). //AllUsersRest @RolesAllowed("Admin")
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                body(user).
                when().
                put("/admin/user").
                then().
                statusCode(200);
        given().
                contentType("application/json").
                header("Authorization", "Bearer " + from(json).get("token")).
                when().
                get("/admin/users").
                then().
                statusCode(200).
                body("size()", equalTo(3));
    }
}
