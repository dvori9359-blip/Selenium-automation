package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BtlBasePage extends BasePage {


    @FindBy(id = "TopQuestions")
    protected WebElement searchField;

 
    @FindBy(id = "ctl00_SiteHeader_reserve_btnSearch")
    protected WebElement searchButton;

 
    @FindBy(id = "ctl00_Topmneu_BranchesHyperLink")
    protected WebElement branchesButton;


    public BtlBasePage(WebDriver driver) {
        super(driver);
    }



    public void search(String textToSearch) {
        searchField.click();
        searchField.clear();
        searchField.sendKeys(textToSearch);
        searchButton.click();
    }

    public BranchesPage goToBranches() {
        branchesButton.click();
        return new BranchesPage(driver);
    }


    public void clickMainMenu(MainMenu menu) {
        String xpathExpression = String.format("//a[contains(text(), '%s')]", menu.getMenuName());
        WebElement menuElement = driver.findElement(By.xpath(xpathExpression));
        menuElement.click();
    }

    public void clickSubMenu(String subMenuText) {
        String xpathExpression = String.format("//a[contains(text(), '%s')]", subMenuText);
        WebElement subMenuElement = driver.findElement(By.xpath(xpathExpression));
        subMenuElement.click();
    }

    public void navigateToPage(MainMenu menu, String subMenuText) {
        clickMainMenu(menu);
        clickSubMenu(subMenuText);
    }

    @FindBy(css = ".breadcrumbs, #breadCrumbs")
    private WebElement breadcrumbsElement;

    public String getBreadcrumbsText() {
        return breadcrumbsElement.getText();
    }
}

