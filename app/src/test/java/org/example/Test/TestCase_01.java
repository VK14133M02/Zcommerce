package org.example.Test;

import org.example.Pages.Home;
import org.example.Pages.Login;
import org.example.Pages.Register;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCase_01 {

    static ChromeDriver driver;

    String lastGeneratedUserEmail;

    @BeforeSuite(alwaysRun = true)
    public static void createDriver(){
        // setup the driver
        WebDriverManager.chromedriver().setup();
        // create new instance for driver
        driver = new ChromeDriver();
        // maximize the window
        driver.manage().window().maximize();
    }

    @Test(description = "Verify the funcanality of login and register")
    public void TestCase01(){
        Boolean status;
        Home home = new Home(driver);
        // Navigate to home page
        home.navigateToHomePage();
        // navigate to login page
        home.ClickOnLoginButton();

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/login");

        // create object for login page
        Login login = new Login(driver);
        //Navigate to login page
        login.navigateLoginPage();
        //click on Sign Up Button
        login.clickOnSignupButton();

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/signup");

        Register register = new Register(driver);
        // navigate to register page
        register.navigateToRegisterPage();
        // register a new user
        status = register.registerUser("pablo", "pablo59@pablo.com", "Pablo@123", true);
        Assert.assertTrue(status, "Not able to Register a user");

        lastGeneratedUserEmail = register.lastGeneratedUserEmail;

        home.logout();        

        home.ClickOnLoginButton();

        status = login.loginUser("pablo",lastGeneratedUserEmail,"Pablo@123");

        Assert.assertTrue(status, "Not able to login the user");

        home.logout();        
    }

    @AfterSuite
    public void closeDriver(){
        driver.close();
        driver.quit();
    }    
}