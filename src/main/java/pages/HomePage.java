package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    // Locators
    private By companyMenu = By.xpath("//a[@id='navbarDropdownMenuLink' and contains(text(), 'Company')]");
    private By careersOption = By.xpath("//a[contains(@href, '/careers/') and text()='Careers']");

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
