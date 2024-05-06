package org.example.Test;

import java.time.Duration;
import java.util.List;

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

public class TestCase_04 {
    String lastGeneratedUserEmail;

    static WebDriver driver;    
    @BeforeSuite(alwaysRun = true)
    public static void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();
    }

    @Test(description="Add to Crt")
    public void TestCase04(){
        Boolean status;
        Home home = new Home(driver);
        // Navigate to home page
        home.navigateToHomePage();
        // navigate to login page
        home.ClickOnLoginButton();
        // create object for login page
        Login login = new Login(driver);
        //Navigate to login page
        login.navigateLoginPage();
        //click on Sign Up Button
        login.clickOnSignupButton();

        Register register = new Register(driver);
        // navigate to register page
        register.navigateToRegisterPage();
        // register a new user
        status = register.registerUser("Chunnu", "Chunnu@gmail.com", "Chunnu@123", true);

        System.out.println("Registration success");
        //search the product        
        status = home.searchForProduct("Surya");
        Assert.assertTrue(status,"Unable to search the product");

        List<WebElement> searchResults = home.getSearchResults();


        for (WebElement webElement : searchResults) {
                // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement,driver);
            // click on the parent element if the product name is matching
            resultelement.clickOnTheSearchResult("Surya hoo1020-913 hoboken area rug, 9' x 13', blue, neutral");
            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));            
        }

        // click on add to cart button
        Product product = new Product(driver);
        product.ClickOnAddToCart();

        System.out.println("Product_1 added to cart");

        // navigate to home page
        home.navigateToHomePage();

        // add another product

        status = home.searchForProduct("Rubies monster");
        Assert.assertTrue(status,"Unable to search the product");

        List<WebElement> searchResults2 = home.getSearchResults();


        for (WebElement webElement : searchResults2) {
                // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement,driver);
            // click on the parent element if the product name is matching
            resultelement.clickOnTheSearchResult("Rubies monster high fright camera action cleo de nile costume, child large");
            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));            
        }

        //click on add to cart
        product.ClickOnAddToCart();

        System.out.println("Product_2 added to cart");  
        
        // navigate to home page
        home.navigateToHomePage();

        String cartPageUrl = home.clickOnCartButton();
        Assert.assertEquals(cartPageUrl, "https://zcommerce.crio.do/cart");
    }



    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
    } 

}
