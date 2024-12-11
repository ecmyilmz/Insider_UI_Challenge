package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CareersPage {
    WebDriver driver;

    private By teams = By.xpath("//h3[@class='category-title-media' and contains(text(), 'Find your calling')]");
    private By locations = By.xpath("//*[@id=\"career-our-location\"]/div/div/div/div[1]/h3");
    private By lifeAtInsider = By.xpath("//h2[@class='elementor-heading-title elementor-size-default' and text()='Life at Insider']");
    private By job = By.xpath("//*[@id=\"find-job-widget\"]/div/div/div[1]/h3");

    // Constructor
    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public boolean areCareerSectionsDisplayed() {
        try {

            scrollToElement(driver.findElement(teams));
            boolean teamsDisplayed = driver.findElement(teams).isDisplayed();

            scrollToElement(driver.findElement(locations));
            boolean locationsDisplayed = driver.findElement(locations).isDisplayed();

            scrollToElement(driver.findElement(lifeAtInsider));
            boolean lifeAtInsiderDisplayed = driver.findElement(lifeAtInsider).isDisplayed();

            scrollToElement(driver.findElement(job));
            boolean jobDisplayed = driver.findElement(job).isDisplayed();

            return locationsDisplayed && teamsDisplayed && lifeAtInsiderDisplayed && jobDisplayed;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper Method: Scroll to an element
    private void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }
}
