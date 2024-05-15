package org.example.Pages;

import java.time.Duration;

import org.example.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cart {
    WebDriver driver;

    @FindBy(xpath = "//button[contains(text(),'buy')]") WebElement checkoutButton;

    SeleniumWrapper seleniumWrapper = new SeleniumWrapper();

    public Cart(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public String clickOnCheckoutButton(){
        try {
            seleniumWrapper.click(checkoutButton, driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlToBe("https://zcommerce.crio.do/checkout"));
            return driver.getCurrentUrl();
        } catch (Exception e) {
            // TODO: handle exception
            return "Error in click on checkout button";
        }
    }
}
