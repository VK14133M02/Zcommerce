package org.example.Pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
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

    public Home(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToHomePage(){
        if(!driver.getCurrentUrl().equals(home_page_URL)){
            driver.get(home_page_URL);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlToBe(home_page_URL));
    }

    public void ClickOnLoginButton(){
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlToBe("https://zcommerce.crio.do/login"));
    }

    public void logout(){
        try{
            userNameButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(logoutButton));

            logoutButton.click();

            wait.until(ExpectedConditions.visibilityOf(loginButton));

        }catch(Exception e){
            System.out.println("Error in logout");
            // e.printStackTrace();
        }
        
    }
}
