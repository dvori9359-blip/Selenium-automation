package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InsurancePage extends BtlBasePage {


    @FindBy(css = "a[href*='Insurance_NotSachir']")
    private WebElement calculatorLink;

    public InsurancePage(WebDriver driver) {
        super(driver);
    }

    public CalculatorPage clickCalculator() {
        calculatorLink.click();
        return new CalculatorPage(driver);
    }
}