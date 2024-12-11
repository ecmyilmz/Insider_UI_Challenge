package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        try {
            // Tarayıcıyı başlatma
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }

            driver.manage().window().maximize();
            driver.get("https://useinsider.com/");

            // Cookie banner ve popup'ları kapatma
            closePopupsAndCookies();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Setup method failed: " + e.getMessage());
        }
    }

    private void closePopupsAndCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));// Maksimum 5 saniye bekler

        try {
            // Cookie banner "Accept All" butonunu bul ve tıkla
            By cookieAcceptButton = By.id("wt-cli-reject-btn");
            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
            System.out.println("Cookie banner kapatıldı.");
        } catch (Exception e) {
            System.out.println("Cookie banner bulunamadı veya zaten kapatılmış.");
        }

        try {
            // Popup kapatma butonunu bul ve tıkla
            By popupCloseButton = By.cssSelector(".ins-close-button");
            wait.until(ExpectedConditions.elementToBeClickable(popupCloseButton)).click();
            System.out.println("Popup kapatıldı.");
        } catch (Exception e) {
            System.out.println("Popup bulunamadı veya zaten kapatılmış.");
        }

    }




    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void takeScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshot.toPath(), new File("screenshots/" + testName + ".png").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
