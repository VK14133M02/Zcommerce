package org.example.Test;

import java.io.IOException;
import java.util.List;

import org.example.DP;
import org.example.DriverSingleton;
import org.example.ReportSingleton;
import org.example.Pages.Home;
import org.example.Pages.SearchResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;

public class TestCase_02 {

    static WebDriver driver; 
    ExtentReports report;
    ExtentTest test;
    
    @BeforeSuite(alwaysRun = true)
    public void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();

        ReportSingleton reportSingleton = ReportSingleton.getInstanceOfSingletonReport();
        report = reportSingleton.getReport();
        test = report.startTest("TestCase02");
    }

    @Test(description = "Verify the search functionality")
    public void TestCase02() throws IOException{
        System.out.println("Testcase02 started successfully");
        boolean status = false;
        Home home = new Home(driver);
        home.navigateToHomePage();
        //search the product        
        status = home.searchForProduct("boss");
        Assert.assertTrue(status,"Unable to search the product");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Testcase02 started successfully");


        // get the list of product
        List<WebElement> searchResults = home.getSearchResults();

        // verify the prduct available or not
        status = searchResults.size() == 0;

        Assert.assertTrue(!status, "No product available to this search");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Able to search the product");


        for (WebElement webElement : searchResults) {
                // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement,driver);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();

            status = elementText.contains("boss");

            Assert.assertTrue(status,"Product name is not present in element text");  
            test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));
            break;                      
        }

        status = home.filter();
        Assert.assertTrue(status,"Filter element is not dislayed");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Filter dropdown is displayed");


        status = home.searchForProduct("roadstar");
        Assert.assertTrue(status,"Unable to search the product");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("Searching unavailable product");


        status = home.isNoResultFound();
        Assert.assertTrue(status,"Result found for the given product");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));

        System.out.println("No result for unavailable product");


        // get the list of product
        searchResults = home.getSearchResults();

        // verify the prduct available or not
        status = searchResults.size() == 0;

        Assert.assertTrue(status, "No product available to this search");
        test.log(LogStatus.INFO, test.addScreenCapture(ReportSingleton.captureScreenShot(driver)));


        System.out.println("Testcase02 Passed successfully");
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
        report.endTest(test);
        report.flush();
    } 
}
