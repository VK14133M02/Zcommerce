package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchResult {
    WebElement parentElement;

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
            parentElement.click();  
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


}
