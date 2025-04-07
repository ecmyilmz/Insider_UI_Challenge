package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;
import pages.QAJobsPage;
import utils.BaseTest;

public class CareersTest extends BaseTest {

    @Test
    public void testCase1_CheckHomePageOpened() {
        HomePage homePage = new HomePage(driver);

        // Step 1: Verify home page is opened
        Assert.assertTrue(homePage.isHomePageOpened(), "Home page is not opened!");
    }

    @Test
    public void testCase2_CheckCareersPageAndSections() {
        HomePage homePage = new HomePage(driver);
        CareersPage careersPage = new CareersPage(driver);

        // Step 1: Navigate to Careers page
        homePage.navigateToCareers();

        // Step 2: Verify Careers page sections are displayed
        Assert.assertTrue(careersPage.areCareerSectionsDisplayed(),
                "Career page sections (Locations, Teams, Life at Insider) are not displayed!");
    }

    @Test
    public void testCase3_FilterQAJobsByLocationAndDepartment() {
        QAJobsPage qaJobsPage = new QAJobsPage(driver);

        // Step 1: Navigate to QA Jobs page
        driver.get("https://useinsider.com/careers/quality-assurance/");

        qaJobsPage.goAllQAJobs();

        // Step 2: Filter jobs
        qaJobsPage.filterJobs("Istanbul, Turkey", "Quality Assurance");

        // Step 3: Verify job list is present
        Assert.assertTrue(qaJobsPage.areJobsFilteredCorrectly("Istanbul, Turkey", "Quality Assurance"),
                "No QA jobs are present for the selected location and department!");
    }

    @Test
    public void testCase4_VerifyJobDetails() {
        QAJobsPage qaJobsPage = new QAJobsPage(driver);

        // Step 1: Navigate to QA Jobs page
        driver.get("https://useinsider.com/careers/quality-assurance/");

        qaJobsPage.goAllQAJobs();

        // Step 2: Filter jobs
        qaJobsPage.filterJobs("All", "Quality Assurance");

        // Step 3: Verify each job's details (Position, Department, Location)
        Assert.assertTrue(qaJobsPage.areJobsFilteredCorrectly("Istanbul, Turkey", "Quality Assurance"),
                "Jobs do not match the selected filters (Position, Department, Location)!");
    }

    @Test
    public void testCase5_CheckLeverApplicationForm() throws InterruptedException {
        QAJobsPage qaJobsPage = new QAJobsPage(driver);

        // Step 1: Navigate to QA Jobs page
        driver.get("https://useinsider.com/careers/quality-assurance/");
        qaJobsPage.goAllQAJobs();
        qaJobsPage.filterJob("All", "Quality Assurance");

        // Step 2: Click 'View Role' button for the first job
        qaJobsPage.hoverAndClickViewRole();

        qaJobsPage.verifyRedirectedURLInNewTab();
    }
}
