package org.example.Test;

import java.util.List;

import org.example.DriverSingleton;
import org.example.Pages.Home;
import org.example.Pages.Login;
import org.example.Pages.Register;
import org.example.Pages.SearchResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import org.testng.Assert;

public class TestCase_03 {
    String lastGeneratedUserEmail;

    static WebDriver driver;    
    @BeforeSuite(alwaysRun = true)
    public static void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();
    }

    @Test
    public void TestCase03(){
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

        //search the product        
        status = home.searchForProduct("Surya");
        Assert.assertTrue(status,"Unable to search the product");
        System.out.println("Testcase02 started successfully");

        List<WebElement> searchResults = home.getSearchResults();


        for (WebElement webElement : searchResults) {
                // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();

            status = elementText.contains("Surya");

            Assert.assertTrue(status,"Product name is not present in element text"); 
            break;            
        }
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
    } 
}