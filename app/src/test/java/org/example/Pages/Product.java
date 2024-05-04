package org.example.Pages;
import java.util.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Product {
    WebDriver driver;
    @FindBy(xpath = "//div[@class='font-bold text-xl md:text-2xl']") WebElement productTitle;

    @FindBy(xpath = "//button[text()='Add to Cart']") WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='flex flex-col gap-6']") WebElement imageElament;

    @FindBy(xpath = "//ul[@aria-label='Pagination']") WebElement paginationButton;

    @FindBy(xpath = "//div[@class='swiper-wrapper']/div") List<WebElement> images;

    @FindBy(css = ".svg-inline--fa.fa-chevron-left.cursor-pointer") WebElement leftButton;

    @FindBy(css = ".svg-inline--fa.fa-chevron-right.cursor-pointer") WebElement roghtButton;


    public Product(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public boolean isContentPresent(String product){
        boolean status = false;
        try {
            status = productTitle.getText().contains(product);
            status = addToCartButton.isDisplayed();
            status = imageElament.isDisplayed();
            status = paginationButton.isDisplayed();
            return status;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    
    // public boolean isImagePresent(){

    // }
}
