package org.example.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.example.DriverSingleton;
import org.example.ReportSingleton;
import org.example.Components.Pagination;
import org.example.Pages.Home;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestCase_05 {
    ExtentReports report;
    ExtentTest test;
    static WebDriver driver;  
    
    @BeforeSuite(alwaysRun = true)
    public void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();

        ReportSingleton reportSingleton = ReportSingleton.getInstanceOfSingletonReport();
        report = reportSingleton.getReport();
        test = report.startTest("TestCase05");
    }

    @Test(description = "Verify the pagination is working fine")
    public void TestCase05() throws IOException{
        boolean status = false;
        List<String> expectedForwordPagination = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        List<String> expectedBackwordPagination = Arrays.asList("120","119","118","117","116","115","114","113","112","111");

        Home home = new Home(driver);
        home.navigateToHomePage();

        Pagination pagination = new Pagination(driver);
        status = pagination.verifyForwordPagination(expectedForwordPagination);
        Assert.assertTrue(status,"Forword Paggination is not working as Expected");
         // Capture screenshot and add to report
        String screenshotPath = ReportSingleton.captureScreenShot(driver);
        System.out.println("Screenshot path: " + screenshotPath);

        test.log(LogStatus.INFO,"Screenshot: "+ test.addScreenCapture(screenshotPath));

        System.out.println("Forword Pagination is working as expected");

        home.navigateToHomePage();

        status = pagination.verifyBackwordPagination(expectedBackwordPagination);
        Assert.assertTrue(status,"Backword Pagination is not working as expected");
        screenshotPath = ReportSingleton.captureScreenShot(driver);
        System.out.println("Screenshot path: " + screenshotPath);

        test.log(LogStatus.INFO,"Screenshot: "+ test.addScreenCapture(screenshotPath));


        System.out.println("Backword button is working fine");
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
        report.endTest(test);
        report.flush();
    }
}
