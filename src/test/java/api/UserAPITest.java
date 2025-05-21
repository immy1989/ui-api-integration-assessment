package api;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigManager;
import utils.ExtentReportManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserAPITest {
    private static String baseUrl;
    private static String createUserEndpoint;
    private static String testUsername;
    private static String testPassword = "Test@1234";
    private static com.aventstack.extentreports.ExtentTest test;

    @BeforeClass
    public void setup() {
        baseUrl = ConfigManager.getProperty("api.base.url");
        createUserEndpoint = ConfigManager.getProperty("api.create.user.endpoint");
        RestAssured.baseURI = baseUrl;
        
        // Initialize ExtentTest
        test = ExtentReportManager.getInstance().createTest("API User Creation Test");
    }

    @Test
    public void testCreateUser() {
        test.log(Status.INFO, "Starting testCreateUser");
        
        // Generate random username for each test run
        testUsername = "testuser_" + System.currentTimeMillis();
        
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s@test.com\"}", 
                testUsername, testPassword, testUsername);

        test.log(Status.INFO, "Creating user with username: " + testUsername);
        
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
            .when()
                .post(createUserEndpoint)
            .then()
                .statusCode(201)
                .body("username", equalTo(testUsername))
                .extract().response();

        test.log(Status.PASS, "User created successfully with username: " + testUsername);
        
        // Store the created user details for UI test
        System.setProperty("testUsername", testUsername);
        System.setProperty("testPassword", testPassword);
        System.setProperty("userEmail", testUsername + "@test.com");
    }

    @AfterMethod
    public void tearDown() {
        ExtentReportManager.getInstance().flush();
    }
}