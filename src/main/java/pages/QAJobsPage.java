package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class QAJobsPage {
    WebDriver driver;

    // Locators
    private By locationFilter = By.id("filter-by-location");
    private By departmentFilter = By.id("filter-by-department");
    private By applyFiltersButton = By.xpath("//button[text()='Apply Filters']");
    private By jobList = By.cssSelector(".jobs-list > .job-card");
    private By viewRoleButton = By.cssSelector(".job-card a.view-role");

    // Constructor
    public QAJobsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void filterJobs(String location, String department) {
        driver.findElement(locationFilter).sendKeys(location);
        driver.findElement(departmentFilter).sendKeys(department);
        driver.findElement(applyFiltersButton).click();
    }

    public boolean areJobsFilteredCorrectly(String location, String department) {
        List<WebElement> jobs = driver.findElements(jobList);

        for (WebElement job : jobs) {
            String jobLocation = job.findElement(By.cssSelector(".job-location")).getText();
            String jobDepartment = job.findElement(By.cssSelector(".job-department")).getText();
            if (!jobLocation.contains(location) || !jobDepartment.contains(department)) {
                return false;
            }
        }
        return true;
    }

    public void clickViewRole() {
        driver.findElement(viewRoleButton).click();
    }

    public boolean isApplicationFormOpened() {
        return driver.getCurrentUrl().contains("lever.co");
    }
}
