package testRest;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import deploy.DeploymentConfiguration;
import org.eclipse.jetty.server.Server;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.Test;

/**
 * OBS: Uses the local tomcat server and not jetty, so the tomcat server has to be running.
 */
public class CurrencyRestTest {

    static Server server;

    public CurrencyRestTest() {
        DeploymentConfiguration.setTestModeOn();
        baseURI = "http://localhost:8080";
        defaultParser = Parser.JSON;
        basePath = "/AngSeedServer/api";
    }

    /**
     * Login as user and getDailyRates. Test that response-body has all
     * currencies.
     */
    @Test
    public void testGetDailyRates() {
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
                statusCode(200).
                body("size()", equalTo(33)).
                body("dateField", hasItems("Nov 5, 2015")).
                body("currency.currencyCode", hasItems("AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "EUR", "GBP", "HKD",
                                "HRK", "HUF", "IDR", "ILS", "INR", "ISK", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN",
                                "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "XDR", "ZAR")).
                body("currency.name", hasItems("Australian dollars", "Bulgarian lev", "Brazilian real", "Canadian dollars",
                                "Swiss francs", "Chinese yuan renminbi", "Czech koruny", "Euro", "Pounds sterling", "Hong Kong dollars",
                                "Croatian kuna", "Hungarian forints", "Indonesian rupiah", "Isreali shekel", "Indian rupee",
                                "Icelandic kronur", "Japanese yen", "South Korean won", "Mexican peso", "Malaysian ringgit",
                                "Norwegian kroner", "New Zealand dollars", "Philippine peso", "Polish zlotys", "Romanian leu",
                                "Russian rouble", "Swedish kronor", "Singapore dollars", "Thai baht", "Turkish lira", "US dollars",
                                "SDR (Calculated **)", "South African rand"));
    }
}
