package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSingleton {
    static WebDriver driver;
    public static void createDriver(){
        // setup the driver
        WebDriverManager.chromedriver().setup();

        // to make the browswe headless
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless","--remote-allow-origins=*");

        // create new instance for driver        
        driver = new ChromeDriver();

        System.out.println("Driver Created");

        // maximize the window
        driver.manage().window().maximize();
    }

    public WebDriver getInstence(){
        if(driver == null){
            createDriver();
        }
        return driver;
    }
}
