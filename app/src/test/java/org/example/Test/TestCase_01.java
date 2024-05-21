package org.example.Test;

import org.example.DP;
import org.example.DriverSingleton;
import org.example.Pages.Home;
import org.example.Pages.Login;
import org.example.Pages.Register;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;
import org.testng.Assert;



public class TestCase_01 {

    String lastGeneratedUserEmail;

    static WebDriver driver;    
    @BeforeSuite(alwaysRun = true)
    public void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();
    }

    @Test(description = "Verify the funcanality of Register and Login")
    public void TestCase01(){
        Boolean status;
        Home home = new Home(driver);
        // Navigate to home page
        home.navigateToHomePage();
        // navigate to login page
        home.ClickOnLoginButton();

        System.out.println("Came to the Register page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/login");

        // create object for login page
        Login login = new Login(driver);
        //Navigate to login page
        login.navigateLoginPage();
        //click on Sign Up Button
        login.clickOnSignupButton();

        System.out.println("Home Page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/signup");

        Register register = new Register(driver);
        // navigate to register page
        register.navigateToRegisterPage();
        // register a new user
        status = register.registerUser("Chunnu", "Chunnu@gmail.com", "Chunnu@123", true);
        Assert.assertTrue(status, "Not able to Register a user");

        System.out.println("Registration success");

        lastGeneratedUserEmail = register.lastGeneratedUserEmail;

        home.logout();        

        home.ClickOnLoginButton();

        status = login.loginUser("Chunnu",lastGeneratedUserEmail,"Chunnu@123");

        Assert.assertTrue(status, "Not able to login the user");

        System.out.println("Login Success");

        home.logout();        
    }

    @Test(description = "Verify re-registration")
    public void TestCase01_1(){
        System.out.println("TestCase01 started");
        Boolean status;
        Home home = new Home(driver);
        // Navigate to home page
        home.navigateToHomePage();
        // navigate to login page
        home.ClickOnLoginButton();

        System.out.println("Came to the Register page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/login");

        // create object for login page
        Login login = new Login(driver);
        //Navigate to login page
        login.navigateLoginPage();
        //click on Sign Up Button
        login.clickOnSignupButton();

        System.out.println("Home Page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/signup");

        Register register = new Register(driver);
        // navigate to register page
        register.navigateToRegisterPage();
        // register a new user
        status = register.registerUser("Chunnu", "Chunnu@gmail.com", "Chunnu@123", true);

        Assert.assertTrue(status, "Not able to Register a user");

        // store last generated usereamil
        lastGeneratedUserEmail = register.lastGeneratedUserEmail;

        System.out.println("Registration success");

        home.logout();

        register.navigateToRegisterPage();
        // again registration
        status = register.registerUser("Chunnu", lastGeneratedUserEmail, "Chunnu@123", false);

        Assert.assertTrue(!status,"able to register with same credentail");
        System.out.println("TestCase01 Ended");
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
    }    
}
