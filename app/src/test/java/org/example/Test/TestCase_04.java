package org.example.Test;

import java.io.IOException;
import java.util.List;

import org.example.DriverSingleton;
import org.example.ReportSingleton;
import org.example.Pages.Cart;
import org.example.Pages.Checkout;
import org.example.Pages.Home;
import org.example.Pages.Login;
import org.example.Pages.Product;
import org.example.Pages.Register;
import org.example.Pages.SearchResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;

public class TestCase_04 {
    String lastGeneratedUserEmail;
    ExtentReports report;
    ExtentTest test;
    static WebDriver driver;    
    @BeforeSuite(alwaysRun = true)
    public void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();

        ReportSingleton reportSingleton = ReportSingleton.getInstanceOfSingletonReport();
        report = reportSingleton.getReport();
        test = report.startTest("TestCase04");
    }

    @Test(description="Add to Crt")
    public void TestCase04() throws IOException{
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
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));


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
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

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
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Clicked on cart Button");

        Cart cart = new Cart(driver);
        String checkoutUrl = cart.clickOnCheckoutButton();
        Assert.assertEquals(checkoutUrl, "https://zcommerce.crio.do/checkout");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Clicked on Checkout Button");

        // add the address on checkout page;
        Checkout checkout = new Checkout(driver);
        status = checkout.enterDeleveryAddress("criodo.co.in", "123456", "bgr", "kr", "in");
        Assert.assertTrue(status, "Not able to fill the address");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Address filled");

        checkout.selectPaymentMethod();

        status = checkout.clickOnOrderButton();
        Assert.assertTrue(status,"Error in the thanks page url");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));
    }



    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
        report.endTest(test);
        report.flush();
    } 

}
