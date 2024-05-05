package org.example.Test;

import java.util.List;
import java.time.Duration;

import org.example.DP;
import org.example.DriverSingleton;
import org.example.Pages.Home;
import org.example.Pages.Login;
import org.example.Pages.Product;
import org.example.Pages.Register;
import org.example.Pages.SearchResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    @Test(description = "Search the product and click on it", dataProvider = "data-provider", dataProviderClass = DP.class)
    public void TestCase03(String name, String email, String password,String product){
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
        status = register.registerUser(name, email, password, true);
        Assert.assertTrue(status, "Not able to Register a user");

        System.out.println("Registration success");

        lastGeneratedUserEmail = register.lastGeneratedUserEmail;

        home.logout();        

        home.ClickOnLoginButton();

        status = login.loginUser(name,lastGeneratedUserEmail,password);

        Assert.assertTrue(status, "Not able to login the user");

        System.out.println("Login Success");

        //search the product        
        status = home.searchForProduct(product);
        Assert.assertTrue(status,"Unable to search the product");
        System.out.println("Testcase02 started successfully");

        List<WebElement> searchResults = home.getSearchResults();


        for (WebElement webElement : searchResults) {
                // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();

            status = elementText.contains(product);

            Assert.assertTrue(status,"Product name is not present in element text"); 
            webElement.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Add to Cart']")));
            System.out.println(driver.getCurrentUrl());
            break;            
        }

        Product productPage = new Product(driver);
        status = productPage.isContentPresent(product);

        Assert.assertTrue(status,"All the content is not displayed");

        status = productPage.isImagePresent();
        Assert.assertTrue(status,"Correct images in not displayed"); 
        
        productPage.review();

        home.logout();       
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
    } 
}
