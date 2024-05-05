package org.example.Pages;

import java.time.Duration;
import java.util.UUID;

import org.example.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Register {
    WebDriver driver;
    String register_page_URL = "https://zcommerce.crio.do/signup";

    public String lastGeneratedUserEmail = "";

    @FindBy(xpath = "//input[@name='name']") WebElement name_inputBox;
    
    @FindBy(xpath = "//input[@name='email']") WebElement email_inputBox;

    @FindBy(xpath = "//input[@name='password']") WebElement password_inputBox;

    @FindBy(xpath = "//button[text()='Register']") WebElement registerButton;


    public Register(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToRegisterPage(){        
        SeleniumWrapper SeleniumWrapper = new SeleniumWrapper();
        SeleniumWrapper.navigteToURL(driver, register_page_URL);
    }

    public boolean registerUser(String userName, String email, String password, boolean makeUserEmailDynamic){

        SeleniumWrapper seleniumWrapper = new SeleniumWrapper();

        // send name in input box
        // name_inputBox.sendKeys(userName);
        seleniumWrapper.sendKey(name_inputBox, userName);

        String dynamicUserEmail;

        String[] userEmail = email.split("@");

        //make user Email dynamic
        if(makeUserEmailDynamic){
            dynamicUserEmail = userEmail[0]+UUID.randomUUID().toString()+"@"+userEmail[1];
        }else{
            dynamicUserEmail = email;
        }

        // send the user email to the inout box
        // email_inputBox.sendKeys(dynamicUserEmail);
        seleniumWrapper.sendKey(email_inputBox, dynamicUserEmail);

        // store last generated userEmail
        this.lastGeneratedUserEmail = dynamicUserEmail;

        // send password in input box
        // password_inputBox.sendKeys(password);
        seleniumWrapper.sendKey(password_inputBox,password);

        // click on register button
        // registerButton.click();
        seleniumWrapper.click(registerButton, driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.or(ExpectedConditions.urlToBe("https://zcommerce.crio.do/"), ExpectedConditions.urlToBe("https://zcommerce.crio.do/signup")));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

        return verifyUserRegisterd(userName);
    }    

    public boolean verifyUserRegisterd(String name){
        try{
            WebElement username = driver.findElement(By.xpath("//div[@class='text-sm font-normal']"));

            return username.getText().equals(name);

        }catch(Exception e){
            return false;
        }
    }
}
