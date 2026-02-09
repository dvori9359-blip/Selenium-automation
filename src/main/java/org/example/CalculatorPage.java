package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CalculatorPage extends BtlBasePage {

    @FindBy(css = "input[id$='rdb_employeType_1']")
    private WebElement yeshivaStudentRadio;

    @FindBy(css = "input[id$='rdb_Gender_0']")
    private WebElement maleGenderRadio;

    @FindBy(css = "input[id$='BirthDate_Date']")
    private WebElement dateOfBirthField;

    @FindBy(xpath = "//input[@value='המשך'] | //button[contains(text(), 'המשך')]")
    private WebElement continueButton;

    @FindBy(css = "input[id$='rdb_GetNechut_1']")
    private WebElement disabilityNoRadio;

    @FindBy(css = "div[id$='_div_Result']")
    private WebElement totalAmountContainer;



    @FindBy(xpath = "//input[contains(@id, 'TerminationDate')]")
    private WebElement terminationDateField;

    @FindBy(xpath = "//input[contains(@id, 'txtAge')]")
    private WebElement ageField;

    @FindBy(xpath = "//input[contains(@id, 'txtIncome')]")
    private WebElement incomeField;

    public CalculatorPage(WebDriver driver) {
        super(driver);
    }


    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(25));
    }


    private void switchToUnemploymentIframe() {

        getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

        int framesCount = driver.findElements(By.tagName("iframe")).size();

        for (int i = 0; i < framesCount; i++) {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(i);

            if (driver.findElements(By.xpath("//input[contains(@id,'TerminationDate')]")).size() > 0) {
                return; 
            }
        }

        throw new RuntimeException("לא נמצא iframe של מחשבון אבטלה");
    }



    public void fillUnemploymentDetails(String date, String age, String income) {

        switchToUnemploymentIframe();

        WebElement dateField = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@id,'TerminationDate')]")
                )
        );

        WebElement ageInput = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@id,'txtAge')]")
                )
        );

        WebElement incomeInput = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@id,'txtIncome')]")
                )
        );

        WebElement continueBtn = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@value='המשך' or contains(text(),'המשך')]")
                )
        );

        dateField.clear();
        dateField.sendKeys(date);

        ageInput.clear();
        ageInput.sendKeys(age);

        incomeInput.clear();
        incomeInput.sendKeys(income);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", continueBtn);
    }

 
    public boolean isUnemploymentResultDisplayed() {
        try {
            driver.switchTo().defaultContent();

            return getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'דמי אבטלה') or contains(text(),'שכר יומי')]")
                    )
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



    private void clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void fillDateOfBirth(String date) {
        clickWithJS(yeshivaStudentRadio);
        clickWithJS(maleGenderRadio);
        dateOfBirthField.clear();
        dateOfBirthField.sendKeys(date);
        clickWithJS(continueButton);
    }

    public boolean isStepTwoReached() {
        return disabilityNoRadio.isDisplayed();
    }

    public void selectNoDisability() {
        clickWithJS(disabilityNoRadio);
        clickWithJS(continueButton);
    }

    public String getTotalResult() {
        return totalAmountContainer.getText();
    }

    public String getHeaderText() {
        return driver.findElement(By.cssSelector("h1")).getText();
    }
}
