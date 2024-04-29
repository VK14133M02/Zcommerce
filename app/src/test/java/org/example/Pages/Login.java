package org.example.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    RemoteWebDriver driver;
    String login_page_URL = "https://zcommerce.crio.do/login";

    @FindBy(linkText = "Sign Up") WebElement SignupButton;
    
    @FindBy(xpath = "//input[@name='email']") WebElement email_inputBox;

    @FindBy(xpath = "//input[@name='password']") WebElement password_inputBox;

    @FindBy(xpath = "//button[text()='Login' and @type='submit']") WebElement loginButton;


    public Login(RemoteWebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateLoginPage(){
        if(!driver.getCurrentUrl().equals(login_page_URL)){
            driver.get(login_page_URL);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        ;
        wait.until(ExpectedConditions.urlToBe(login_page_URL));
    }

    public void clickOnSignupButton(){
        SignupButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlToBe("https://zcommerce.crio.do/signup"));
    }

    public boolean loginUser(String name, String email, String password){
        email_inputBox.sendKeys(email);
        password_inputBox.sendKeys(password);
        loginButton.click();

        // FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
        //         .pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
        // wait.until(ExpectedConditions.invisibilityOf(loginButton));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlToBe("https://zcommerce.crio.do/"));

        return isUserLoggedIn(name);
    }

    public boolean isUserLoggedIn(String name){
        try{
            WebElement username = driver.findElement(By.xpath("//div[@class='text-sm font-normal']"));

            return username.getText().equals(name);

        }catch(Exception e){
            return false;
        }
    }
}
