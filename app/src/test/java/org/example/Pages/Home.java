package org.example.Pages;

import java.time.Duration;
import java.util.ArrayList;
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

public class Home {
    WebDriver driver;
    String home_page_URL = "https://zcommerce.crio.do/";

    @FindBy(xpath = "//button[text()='Login']") WebElement loginButton;

    @FindBy(xpath = "//button[@class='chakra-button css-1uzlual']") WebElement userNameButton;

    @FindBy(xpath = "//h3[text()='Logout']") WebElement logoutButton;

    @FindBy(xpath = "//input[@class='chakra-input css-x8ssu3']") WebElement inputBoxElement;

    @FindBy(className = "css-b62m3t-container") WebElement dropdown;

    @FindBy(className = "css-1nmdiq5-menu") WebElement dropdownElement;

    @FindBy(xpath = "//span[text()='We could not find any matches for']") WebElement noResult;

    SeleniumWrapper seleniumWrapper = new SeleniumWrapper();


    public Home(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToHomePage(){
        seleniumWrapper.navigteToURL(driver, home_page_URL);
    }

    public void ClickOnLoginButton(){
        seleniumWrapper.click(loginButton, driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlToBe("https://zcommerce.crio.do/login"));
    }

    public void logout(){
        try{
            // userNameButton.click();
            seleniumWrapper.click(userNameButton, driver);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(logoutButton));

            // logoutButton.click();
            seleniumWrapper.click(logoutButton, driver);

            wait.until(ExpectedConditions.visibilityOf(loginButton));

        }catch(Exception e){
            System.out.println("Error in logout");
            // e.printStackTrace();
        }        
    }

    public Boolean searchForProduct(String product){
        try {
            // clear the search box
            // inputBoxElement.clear();
            // send the key in search box
            // inputBoxElement.sendKeys(product);
            seleniumWrapper.sendKey(inputBoxElement, product);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='flex flex-col gap-2 min-w-[200px] max-w-[350px] cursor-pointer']")),
            ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='We could not find any matches for']"))));
            
            Thread.sleep(3000);
            
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            status = noResult.isDisplayed();
            return status;
        } catch (Exception e) {
            return status;
        }
    }



    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {};
        try {
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElements(By.xpath("//div[@class='flex flex-col gap-2 min-w-[200px] max-w-[350px] cursor-pointer']"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }


    public boolean filter(){
        try {
            seleniumWrapper.click(dropdown, driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("css-1nmdiq5-menu")));

            return dropdownElement.isDisplayed();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }        
    }

}
