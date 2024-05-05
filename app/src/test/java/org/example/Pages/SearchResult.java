package org.example.Pages;

import org.example.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchResult {
    WebElement parentElement;

    SeleniumWrapper seleniumWrapper = new SeleniumWrapper();

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    public String getTitleofResult() {
        String titleOfSearchResult = "";
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        WebElement element = parentElement.findElement(By.className("truncate"));
        titleOfSearchResult = element.getText();
        return titleOfSearchResult;
    }

    public void clickOnTheSearchResult(){
        try {
            seleniumWrapper.click(parentElement, null); 
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void addToCart(String productName){
        try {
            WebElement cartButton = parentElement.findElement(By.xpath(String.format("//child::p[text()='%s']//parent::div//following-sibling::button[contains(text(),'CART')]",productName)));
            seleniumWrapper.click(cartButton, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


}
