package org.example.Components;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.example.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Pagination {
    WebDriver driver;
    @FindBy(className = "previous") WebElement previousButton;

    @FindBy(className = "next") WebElement nextButton;

    // @FindBy(xpath = "//a[@rel='canonical']/div") WebElement currentPage;

    @FindBy(xpath = "//a[@aria-label='Page 120']") WebElement lastPage;

    SeleniumWrapper seleniumWrapper = new SeleniumWrapper();


    public Pagination(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);    
    }

    public boolean verifyForwordPagination(List<String> expectedForwordPagination){        
        // List<String> expectedForwordPagination = Arrays.asList("1","2","3","4","5","6","7","8","8","10");
        try {
            String previousButtonIsDisabled = previousButton.findElement(By.tagName("a")).getAttribute("aria-disabled");
            if(!previousButtonIsDisabled.equals("true")){
                System.out.println("Previous Button should disabled");
                return false;
            }

            for(int i=0;i<10;i++){
                String currentPage = driver.findElement(By.xpath("//a[@rel='canonical']/div")).getText();
                if(!currentPage.equals(expectedForwordPagination.get(i))){
                    System.out.println("Next Paggination is not working as expected "+i+1);
                    return false;
                }
                seleniumWrapper.click(nextButton, driver);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.attributeContains(By.xpath("//a[@rel='canonical']"),"aria-label",Integer.toString(i+2)));
            }

            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyBackwordPagination(List<String> expectedBackwordPagination){
        try {
            seleniumWrapper.click(lastPage, driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.attributeToBe(By.xpath("//a[@aria-label='Next page']"),"aria-disabled","true"));

            String nextButtonIsDisabled = nextButton.findElement(By.tagName("a")).getAttribute("aria-disabled");
            if(!nextButtonIsDisabled.equals("true")){
                System.out.println("Next Button should disabled");
                return false;
            }

            for(int i=0;i<10;i++){
                wait.until(ExpectedConditions.attributeContains(By.xpath("//a[@rel='canonical']"),"aria-label",expectedBackwordPagination.get(i)));
                String currentPage = driver.findElement(By.xpath("//a[@rel='canonical']/div")).getText();
                if(!currentPage.equals(expectedBackwordPagination.get(i))){
                    System.out.println("Previous pagination button is not working as expected");
                    return false;
                }
                seleniumWrapper.click(previousButton, driver);
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;

        }
    }
}
