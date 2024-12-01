package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage {
    WebDriver driver;

    // Locators
    private By locationsBlock = By.id("locations");
    private By teamsBlock = By.id("teams");
    private By lifeAtInsiderBlock = By.id("life-at-insider");
    private By qaJobsLink = By.xpath("//a[contains(@href, '/quality-assurance')]");

    // Constructor
    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public boolean areCareerSectionsDisplayed() {
        return driver.findElement(locationsBlock).isDisplayed() &&
                driver.findElement(teamsBlock).isDisplayed() &&
                driver.findElement(lifeAtInsiderBlock).isDisplayed();
    }

    public void navigateToQAJobs() {
        driver.findElement(qaJobsLink).click();
    }
}
