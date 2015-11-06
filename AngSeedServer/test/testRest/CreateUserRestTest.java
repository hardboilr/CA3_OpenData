package testRest;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import deploy.DeploymentConfiguration;
import entity.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;
import rest.ApplicationConfig;

public class CreateUserRestTest {

    static Server server;

    public CreateUserRestTest() {
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

    @Test
    public void createNewUser() {
        User user1 = new User("hans", "1234");
        //Create a new user
        given().contentType("application/json").
                body(user1).
                when().
                post("/create").
                then().
                statusCode(201).
                body("userName", equalTo("hans")).
                body("roles", hasItems("User"));
        //Create a new user
        User user2 = new User("jens", "1234");
        given().contentType("application/json").
                body(user2).
                when().
                post("/create").
                then().
                statusCode(201).
                body("userName", equalTo("jens")).
                body("roles", hasItems("User"));
    }

    @Test
    public void createExistingUser() {
        User user1 = new User("kurt", "1234");
        //Create a new user 
        given().contentType("application/json").
                body(user1).
                when().
                post("/create").
                then().
                statusCode(201).
                body("userName", equalTo("kurt")).
                body("roles", hasItems("User"));
        //Try to create the same user again
        User user2 = new User("kurt", "1234");
        given().contentType("application/json").
                body(user1).
                when().
                post("/create").
                then().
                statusCode(409);
    }
}
