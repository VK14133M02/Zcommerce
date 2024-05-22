package org.example;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {
    public void click(WebElement element, WebDriver driver){
        try {
            if(element.isDisplayed()){
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void sendKey(WebElement inputBox, String keyToSend){
        try {
            if(inputBox.isDisplayed()){
                inputBox.clear();
                inputBox.sendKeys(keyToSend);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void navigteToURL(WebDriver driver,String url){
        try {
            if(!driver.getCurrentUrl().equals(url)){
                driver.get(url);
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));        
            wait.until(ExpectedConditions.urlToBe(url));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
