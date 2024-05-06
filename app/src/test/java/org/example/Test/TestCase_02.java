package org.example.Test;

import java.util.List;

import org.example.DP;
import org.example.DriverSingleton;
import org.example.Pages.Home;
import org.example.Pages.SearchResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.annotations.*;
import org.testng.Assert;

public class TestCase_02 {

    static WebDriver driver; 
    
    @BeforeSuite(alwaysRun = true)
    public static void createDriver(){
        DriverSingleton driverSingleton = new DriverSingleton();
        driver = driverSingleton.getInstence();
    }

    @Test(description = "Verify the search functionality")
    public void TestCase02(){
        System.out.println("Testcase02 started successfully");
        boolean status = false;
        Home home = new Home(driver);
        home.navigateToHomePage();
        //search the product        
        status = home.searchForProduct("boss");
        Assert.assertTrue(status,"Unable to search the product");
        System.out.println("Testcase02 started successfully");


        // get the list of product
        List<WebElement> searchResults = home.getSearchResults();

        // verify the prduct available or not
        status = searchResults.size() == 0;

        Assert.assertTrue(!status, "No product available to this search");
        System.out.println("Able to search the product");


        for (WebElement webElement : searchResults) {
                // Create a SearchResult object from the parent element
            SearchResult resultelement = new SearchResult(webElement,driver);

            // Verify that all results contain the searched text
            String elementText = resultelement.getTitleofResult();

            status = elementText.contains("boss");

            Assert.assertTrue(status,"Product name is not present in element text");  
            break;                      
        }

            status = home.filter();
            Assert.assertTrue(status,"Filter element is not dislayed");
            System.out.println("Filter dropdown is displayed");


            status = home.searchForProduct("roadstar");
            Assert.assertTrue(status,"Unable to search the product");
            System.out.println("Searching unavailable product");


            status = home.isNoResultFound();
            Assert.assertTrue(status,"Result found for the given product");
            System.out.println("No result for unavailable product");


            // get the list of product
            searchResults = home.getSearchResults();

            // verify the prduct available or not
            status = searchResults.size() == 0;

            Assert.assertTrue(status, "No product available to this search");

            System.out.println("Testcase02 Passed successfully");
    }

    @AfterSuite
    public void closeDriver(){
        // driver.close();
        driver.quit();
    } 
}
