package ui;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import utils.ConfigManager;
import utils.ExtentReportManager;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MobileAppTest {
    private static AppiumDriver<MobileElement> driver;
    private static com.aventstack.extentreports.ExtentTest test;
    private String testUsername;
    private String testPassword;
    private String userEmail;

    @BeforeClass
    public void setup() throws Exception {
        // Get user credentials created by API test
        testUsername = System.getProperty("testUsername");
        testPassword = System.getProperty("testPassword");
        userEmail = System.getProperty("userEmail");
        
        if (testUsername == null || testPassword == null) {
            throw new RuntimeException("User credentials not available from API test");
        }
        
        // Initialize ExtentTest
        test = ExtentReportManager.getInstance().createTest("Mobile App Login Test");
        
        // Initialize Appium driver
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigManager.getProperty("platform.name"));
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigManager.getProperty("platform.version"));
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigManager.getProperty("device.name"));
        caps.setCapability("appPackage", ConfigManager.getProperty("app.package"));
        caps.setCapability("appActivity", ConfigManager.getProperty("app.activity"));
        
        URL appiumServerUrl = new URL(ConfigManager.getProperty("appium.server.url"));
        driver = new AndroidDriver<>(appiumServerUrl, caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testLoginAndProfileValidation() {
        test.log(Status.INFO, "Starting testLoginAndProfileValidation");
        
        try {
            // Login to the app (replace with your actual app login flow)
            MobileElement usernameField = driver.findElementById("com.example.app:id/username");
            MobileElement passwordField = driver.findElementById("com.example.app:id/password");
            MobileElement loginButton = driver.findElementById("com.example.app:id/loginBtn");
            
            usernameField.sendKeys(testUsername);
            passwordField.sendKeys(testPassword);
            loginButton.click();
            
            test.log(Status.INFO, "Logged in successfully with username: " + testUsername);
            
            // Navigate to profile (replace with your actual navigation)
            MobileElement profileButton = driver.findElementById("com.example.app:id/profile");
            profileButton.click();
            
            // Validate profile data matches API data
            MobileElement profileUsername = driver.findElementById("com.example.app:id/profileUsername");
            MobileElement profileEmail = driver.findElementById("com.example.app:id/profileEmail");
            
            String actualUsername = profileUsername.getText();
            String actualEmail = profileEmail.getText();
            
            test.log(Status.INFO, "Validating profile data against API data");
            test.log(Status.INFO, "Expected Username: " + testUsername + ", Actual: " + actualUsername);
            test.log(Status.INFO, "Expected Email: " + userEmail + ", Actual: " + actualEmail);
            
            if (actualUsername.equals(testUsername)) {
                test.log(Status.PASS, "Username matches API data");
            } else {
                test.log(Status.FAIL, "Username does not match API data");
            }
            
            if (actualEmail.equals(userEmail)) {
                test.log(Status.PASS, "Email matches API data");
            } else {
                test.log(Status.FAIL, "Email does not match API data");
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed with exception: " + e.getMessage());
            throw e;
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        ExtentReportManager.getInstance().flush();
    }
}