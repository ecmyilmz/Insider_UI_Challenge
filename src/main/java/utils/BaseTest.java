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
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.out.println("Chrome tarayıcısı başlatılıyor...");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.out.println("Firefox tarayıcısı başlatılıyor...");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Desteklenmeyen tarayıcı: " + browser);
        }

        driver.manage().window().maximize();
        driver.get("https://useinsider.com/");
        // Cookie banner ve popup'ları kapatma
        closePopupsAndCookies();
    }

    private void closePopupsAndCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Maksimum 30 saniye bekler

        try {
            // Cookie banner "Accept All" butonunu bul ve tıkla
            By cookieAcceptButton = By.id("wt-cli-reject-btn");
            wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already handled.");
        }

        try {
            // Popup kapatma butonunu bul ve tıkla
            By popupCloseButton = By.cssSelector(".ins-close-button");
            wait.until(ExpectedConditions.elementToBeClickable(popupCloseButton)).click();
        } catch (Exception e) {
            System.out.println("Popup not found or already handled.");
        }
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        // Test başarısız olursa ekran görüntüsü al
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName(); // Test case adı
            takeScreenshot(testName);
        }

        // Tarayıcıyı kapat
        if (driver != null) {
            driver.quit();
        }
    }

    public void takeScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Klasör kontrolü ve oluşturma
            Path screenshotsDir = Paths.get("screenshots");
            if (!Files.exists(screenshotsDir)) {
                Files.createDirectories(screenshotsDir);
            }

            // Screenshot kaydetme
            Files.copy(screenshot.toPath(), screenshotsDir.resolve(testName + ".png"));
            System.out.println("Screenshot saved: " + screenshotsDir.resolve(testName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
