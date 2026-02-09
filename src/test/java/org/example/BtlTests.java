package org.example;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class BtlTests {
    private WebDriver driver;
    private HomePage homePage;
    private static ExtentReports extent;
    private ExtentTest test;

    @BeforeAll
    public static void initReport() {
        // הגדרת הדוח בתיקיית target
        ExtentSparkReporter spark = new ExtentSparkReporter("target/BtlReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeEach
    public void setup(TestInfo testInfo) {
        // יצירת דרייבר חדש לכל טסט למניעת שגיאות Session
        driver = DriverFactory.getDriver();
        // הגדרת המתנה מרובה לאתר איטי
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://www.btl.gov.il/Pages/default.aspx");

        // הגדלת חלון בצורה בטוחה
        try { driver.manage().window().maximize(); } catch (Exception e) {}

        homePage = new HomePage(driver);
        test = extent.createTest(testInfo.getDisplayName());
    }

    @Test
    @DisplayName("תסריט חישוב דמי אבטלה") // סעיף 7
    public void testUnemploymentCalculation() throws InterruptedException {
        test.info("ניווט ישיר למחשבון אבטלה");
        driver.get("https://www.btl.gov.il/Benefits/Unemployment/Pages/Maxm_Scm.aspx");
        Thread.sleep(7000); // המתנה לטעינת ה-iframe

        CalculatorPage calculator = new CalculatorPage(driver);
        String date = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        test.info("מילוי פרטים במחשבון בתוך ה-iframe");
        calculator.fillUnemploymentDetails(date, "35", "12000");

        test.info("המתנה לטעינת התוצאות");
        Thread.sleep(12000);

        // בדיקה שהתוצאות הופיעו
        boolean resultsVisible = calculator.isUnemploymentResultDisplayed();

        if(resultsVisible) {
            test.pass("תוצאות החישוב (שכר יומי, דמי אבטלה ליום/חודש) הוצגו בהצלחה");
        } else {
            test.fail("תוצאות החישוב לא הופיעו על המסך");
        }

        Assertions.assertTrue(resultsVisible, "החישוב נכשל: הנתונים לא הופיעו בדף התוצאות");
    }

    @ParameterizedTest // סעיף 8
    @DisplayName("בדיקת דפים בשימוש ב-Parametrize")
    @CsvSource({
            "אבטלה, אבטלה",
            "זקנה, אזרח ותיק",
            "ילדים, ילדים",
            "נכות, נכות",
            "איבה, נפגעי פעולות איבה"
    })
    public void testBreadcrumbsParametrized(String menuOption, String expectedText) throws InterruptedException {
        test.info("בודק ניווט עבור: " + menuOption);
        homePage.navigateToPage(MainMenu.KITZBAOT_VE_HATAVOT, menuOption);
        Thread.sleep(5000);

        String breadcrumbs = homePage.getBreadcrumbsText();
        boolean isValid = breadcrumbs.contains(expectedText);

        if(isValid) {
            test.pass("ניווט תקין ל-" + menuOption + ". נמצא ב-Breadcrumbs: " + expectedText);
        } else {
            test.fail("הניווט נכשל. נמצא: " + breadcrumbs);
        }

        Assertions.assertTrue(isValid, "הניווט נכשל עבור " + menuOption);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // סגירה נקייה של הדפדפן
        }
    }

    @AfterAll
    public static void flushReport() {
        if (extent != null) {
            extent.flush(); // כתיבה סופית של הדוח
        }
    }
}