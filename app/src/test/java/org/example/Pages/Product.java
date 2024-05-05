package org.example.Pages;
import java.util.*;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Product {
    WebDriver driver;
    @FindBy(xpath = "//div[@class='font-bold text-xl md:text-2xl']") WebElement productTitle;

    @FindBy(xpath = "//button[text()='Add to Cart']") WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='flex flex-col gap-6']") WebElement imageElament;

    @FindBy(xpath = "//ul[@aria-label='Pagination']") WebElement paginationButton;

    @FindBy(xpath = "//div[@class='swiper-wrapper']/div") List<WebElement> images;

    @FindBy(css = ".svg-inline--fa.fa-chevron-left.cursor-pointer") WebElement leftButton;

    @FindBy(css = ".svg-inline--fa.fa-chevron-right.cursor-pointer") WebElement rightButton;

    @FindBy(xpath = "//ul[@aria-label='Pagination']//child::li") List<WebElement> reviewPaginatinButtons;

    @FindBy(className = "next") WebElement nextButton;

    @FindBy(xpath = "//div[@class='flex gap-2 items-center']") List<WebElement> reviews;


    public Product(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    // Verifng all the content should be present in the product page;
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
    
    // Click on the scroll button to scroll the image Click on the multiple image to change the image and verify the image is changing and it is similar to the clicked image.
    public boolean isImagePresent(){
        try {

            // To click on button to scroll the image
            // if(images.size() > 3){
            //     for(int i=3;i<images.size();i++){
            //         leftButton.click();
            //     }
            // }


            // if(images.size() > 3){
            //     Actions actions = new Actions(driver);
            //     actions.click(leftButton).perform();
            //     // leftButton.click();
            //     System.out.println("Left Button Clicked ********************************************");
            // }

            

            // Verifing the HeaderImage is same after click on scroll image;
            for(int i=0;i<3;i++){
                // click on the image
                images.get(i).click();
                // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                // wait.until(ExpectedConditions.)
                Thread.sleep(2000);                

                //getting the attribute for both scroll and header image
                String headerImageSrc = images.get(i).findElement(By.xpath("//ancestor::div[@class='flex flex-col gap-6']//child::img[@class='md:h-[400px] h-[300px] max-w-[400px] w-full rounded-lg']")).getAttribute("src");
                // System.out.println(headerImageSrc);
                String imageSrc = images.get(i).findElement(By.tagName("img")).getAttribute("src");
                // System.out.println(imageSrc);
                // vverifing the src fro both;
                if(!imageSrc.equals(headerImageSrc)){
                    System.out.println("Header image is not matching with scroll image");
                    return false;
                }
            }
            System.out.println("All the Header image is matching with scroll image");
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }        
    }

    // click on scroll button for review and store the name of all reviewer for all the page;
    public void review(){
        //List to store all the reviewer name and rating
        ArrayList<String> reviewer = new ArrayList<>();
        // Run the loop to click on button
        for(int i=0;i<reviewPaginatinButtons.size()-2;i++){
            // run the loop on reviewer to store it in list
            for(WebElement review : reviews){
                String reviewerName = review.findElement(By.className("font-medium")).getText();
                String reviewCount = review.findElement(By.xpath("//following-sibling::div[@class='star-ratings']")).getAttribute("title");
                reviewer.add(reviewerName);
                reviewer.add(reviewCount);
            }
            nextButton.click();
        }
        System.out.println(reviewer);
    }    
}
