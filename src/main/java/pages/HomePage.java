package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    // Locators
    private By companyMenu = By.xpath("//nav//a[text()='Company']");
    private By careersOption = By.xpath("//nav//a[text()='Careers']");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void navigateToCareers() {
        driver.findElement(companyMenu).click();
        driver.findElement(careersOption).click();
    }

    public boolean isHomePageOpened() {
        return driver.getTitle().contains("Insider");
    }
}
