package org.example.Test;

import java.io.IOException;

import org.example.DP;
import org.example.DriverSingleton;
import org.example.ReportSingleton;
import org.example.Pages.Home;
import org.example.Pages.Login;
import org.example.Pages.Register;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;



public class TestCase_01 {

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
        test = report.startTest("TestCase01");
    }

    @Test(description = "Verify the funcanality of Register and Login")
    public void TestCase01() throws IOException{
        Boolean status;
        Home home = new Home(driver);
        // Navigate to home page
        home.navigateToHomePage();
        // navigate to login page
        home.ClickOnLoginButton();

        System.out.println("Came to the Register page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/login");
        test.log(LogStatus.INFO,"Screenshot: "+ test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        // create object for login page
        Login login = new Login(driver);
        //Navigate to login page
        login.navigateLoginPage();
        //click on Sign Up Button
        login.clickOnSignupButton();

        System.out.println("Home Page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/signup");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));


        Register register = new Register(driver);
        // navigate to register page
        register.navigateToRegisterPage();
        // register a new user
        status = register.registerUser("Chunnu", "Chunnu@gmail.com", "Chunnu@123", true);
        Assert.assertTrue(status, "Not able to Register a user");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));


        System.out.println("Registration success");

        lastGeneratedUserEmail = register.lastGeneratedUserEmail;

        home.logout();        

        home.ClickOnLoginButton();

        status = login.loginUser("Chunnu",lastGeneratedUserEmail,"Chunnu@123");

        Assert.assertTrue(status, "Not able to login the user");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Login Success");

        home.logout();        
    }

    @Test(description = "Verify re-registration")
    public void TestCase01_1() throws IOException{
        System.out.println("TestCase01 started");
        Boolean status;
        Home home = new Home(driver);
        // Navigate to home page
        home.navigateToHomePage();
        // navigate to login page
        home.ClickOnLoginButton();

        System.out.println("Came to the Register page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/login");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));


        // create object for login page
        Login login = new Login(driver);
        //Navigate to login page
        login.navigateLoginPage();
        //click on Sign Up Button
        login.clickOnSignupButton();

        System.out.println("Home Page");

        Assert.assertEquals(driver.getCurrentUrl(),"https://zcommerce.crio.do/signup");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));


        Register register = new Register(driver);
        // navigate to register page
        register.navigateToRegisterPage();
        // register a new user
        status = register.registerUser("Chunnu", "Chunnu@gmail.com", "Chunnu@123", true);

        Assert.assertTrue(status, "Not able to Register a user");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        // store last generated usereamil
        lastGeneratedUserEmail = register.lastGeneratedUserEmail;

        System.out.println("Registration success");

        home.logout();

        register.navigateToRegisterPage();
        // again registration
        status = register.registerUser("Chunnu", lastGeneratedUserEmail, "Chunnu@123", false);

        Assert.assertTrue(!status,"able to register with same credentail");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));
        System.out.println("TestCase01 Ended");
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();

        report.endTest(test);
        report.flush();
    }    
}
