package org.example.Pages;

import java.time.Duration;

import org.example.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;
    WebDriver driver;


    SeleniumWrapper seleniumWrapper = new SeleniumWrapper();

    public SearchResult(WebElement SearchResultElement, WebDriver driver) {
        this.parentElement = SearchResultElement;
        this.driver = driver;
    }

    public String getTitleofResult() {
        String titleOfSearchResult = "";
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        WebElement element = parentElement.findElement(By.className("truncate"));
        titleOfSearchResult = element.getText();
        return titleOfSearchResult;
    }

    // if the product name is matching then we will click on the element;
    public void clickOnTheSearchResult(String productName){
        try {
            String title = parentElement.findElement(By.xpath("//p[@class='font-bold line-clamp-2 text-ellipsis truncate']")).getText();
            if(title.equals(productName)){ 
                seleniumWrapper.click(parentElement, driver);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Add to Cart']")));
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
            System.out.println("Something error");
        }
    }












    // public void addToCart(String productName){
    //     try {
    //         WebElement cartButton = parentElement.findElement(By.xpath(String.format("//child::p[text()='%s']//parent::div//following-sibling::button[contains(text(),'CART')]",productName)));
    //         seleniumWrapper.click(cartButton, driver);
    //         // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         e.printStackTrace();
    //     }
    // }
}
