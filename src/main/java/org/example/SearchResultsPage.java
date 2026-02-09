package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends BtlBasePage {


    @FindBy(css = ".path")
    private WebElement resultsHeader;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }


    public boolean isResultFound(String text) {
        try {
            String bodyText = driver.findElement(By.tagName("body")).getText().replace("\n", " ");
            return bodyText.contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}