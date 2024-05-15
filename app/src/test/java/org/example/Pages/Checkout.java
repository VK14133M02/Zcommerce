package org.example.Pages;

import java.time.Duration;

import org.example.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    WebDriver driver;

    @FindBy(xpath = "//span[contains(text(),'Delivery Address')]") WebElement deleveryAddressButton;

    @FindBy(name = "fullAddress") WebElement addressInput;

    @FindBy(name = "pinCode") WebElement pinCodeInput;

    @FindBy(name = "city") WebElement cityInput;

    @FindBy(name = "state") WebElement stateInput;

    @FindBy(name = "country") WebElement countryInput;

    @FindBy(xpath = "//button[text()='Save Address']") WebElement saveAddressButton;

    @FindBy(xpath = "//div[@class='chakra-alert__desc css-zycdy9']") WebElement alert;

    @FindBy(xpath = "//span[text()='Payment Method']") WebElement paymentMethodButton;

    @FindBy(xpath =  "//span[text()='Cash On Delivery']") WebElement cashOnDeleveryButton;

    @FindBy(xpath = "//button[text()='Order Now']") WebElement orderNow;




    SeleniumWrapper seleniumWrapper = new SeleniumWrapper();

    public Checkout(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public boolean enterDeleveryAddress(String address,String pin, String city, String state, String country){
        boolean status = false;
        try {
            // seleniumWrapper.click(deleveryAddressButton, driver);
            Actions action = new Actions(driver);
            action.click(deleveryAddressButton).perform();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(addressInput));

            seleniumWrapper.sendKey(addressInput, address);

            seleniumWrapper.sendKey(pinCodeInput, pin);

            seleniumWrapper.sendKey(cityInput, city);

            seleniumWrapper.sendKey(stateInput, state);

            seleniumWrapper.sendKey(countryInput, country);

            seleniumWrapper.click(saveAddressButton, driver);

            wait.until(ExpectedConditions.visibilityOf(alert));

            String alertText = alert.getText();

            return alertText.equals("Address added successfully");

        } catch (Exception e) {
            // TODO: handle exception
            return status;
        }
    }

    public void selectPaymentMethod(){
        try {
            seleniumWrapper.click(paymentMethodButton, driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOf(cashOnDeleveryButton));
            seleniumWrapper.click(cashOnDeleveryButton, driver);
            System.out.println("clicked on cash on delevery button");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error on click on cash on delevery button");

        }
    }

    public boolean clickOnOrderButton(){
        try {
            // seleniumWrapper.click(orderNow, driver);
            Actions actions = new Actions(driver);
            actions.click(orderNow).perform();
            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            // wait.until(ExpectedConditions.urlContains("thanks"));
            // Thread.sleep(3000);
            // System.out.println(driver.getCurrentUrl());
            // return driver.getCurrentUrl().contains("thanks");
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }


}
