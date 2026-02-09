package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BranchesPage extends BtlBasePage {

    @FindBy(css = "a.SnifName") 
    private WebElement firstBranchLink;

    @FindBy(xpath = "//*[contains(text(), 'כתובת')]")
    private WebElement addressInfo;

    @FindBy(xpath = "//*[contains(text(), 'קבלת קהל')]")
    private WebElement receptionInfo;

    @FindBy(xpath = "//*[contains(text(), 'מענה טלפוני')]")
    private WebElement phoneInfo;

    public BranchesPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnFirstBranch() {
        firstBranchLink.click();
    }

    public boolean isInfoDisplayed() {
        return addressInfo.isDisplayed() && receptionInfo.isDisplayed() && phoneInfo.isDisplayed();
    }
}