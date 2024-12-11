package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.List;

public class QAJobsPage {
    WebDriver driver;

    // Locators
    private By allQAJobs = By.xpath("//a[contains(text(), 'See all QA jobs')]");
    private By locationFilter = By.xpath("//*[@id=\"top-filter-form\"]/div[1]/span/span[1]/span/span[2]");
    private By locationOptions = By.xpath("//ul[@id='select2-filter-by-location-results']//li[contains(@class, 'select2-results__option')]");
    private By departmentFilter = By.id("select2-filter-by-department-container");
    private By departmentOptions = By.xpath("//ul[@id='select2-filter-by-department-results']//li[contains(@class, 'select2-results__option')]");
    private By jobList =By.cssSelector(".position-list-item-wrapper");
    private By viewRoleButton = By.xpath("//*[@id=\"jobs-list\"]/div[1]/div/a");



    // Constructor
    public QAJobsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goAllQAJobs() {
        driver.findElement(allQAJobs).click();

    }

    // Actions
    public void filterJobs(String location, String department) {
        // Location filter alanına tıkla
        driver.findElement(locationFilter).click();

        // Location listesinde verilen location değerini seç
        List<WebElement> locations = driver.findElements(locationOptions);
        for (WebElement loc : locations) {
            if (loc.getText().equals(location)) {
                loc.click();
                break;
            }
        }

        // Department filter alanına tıkla
        driver.findElement(departmentFilter).click();

        // Department listesinde verilen department değerini seç
        List<WebElement> departments = driver.findElements(departmentOptions);
        for (WebElement dep : departments) {
            if (dep.getText().equals(department)) {
                dep.click();
                break;
            }
        }
    }

    public boolean areJobsFilteredCorrectly(String location, String department) {
        // Kartları temsil eden listeyi seçmek için locators
        List<WebElement> jobs = driver.findElements(jobList);


        for (WebElement job : jobs) {
            // Her kartın içindeki job location ve department bilgilerini seçmek için CSS selectors
            String jobLocation = job.findElement(By.cssSelector(".position-location.text-large")).getText();
            String jobDepartment = job.findElement(By.cssSelector(".position-department.text-large.font-weight-600.text-primary")).getText();


            // Eğer location veya department eşleşmiyorsa false döndür
            if (!jobLocation.trim().equalsIgnoreCase(location.trim()) || !jobDepartment.trim().equalsIgnoreCase(department.trim())) {
                return false;
            }

        }

        // Tüm kartlar doğru filtrelenmişse true döndür
        return true;
    }


    public void hoverAndClickViewRole() throws InterruptedException {
            Thread.sleep(15000);
            // Butonun bulunduğu iş ilanını seçin
            WebElement jobCard = driver.findElement(By.xpath("//*[@id=\"jobs-list\"]/div[1]/div"));
            jobCard.click();
            Thread.sleep(15000);
            // Fareyi jobCard üzerine getirin (hover)
            Actions actions = new Actions(driver);
            actions.moveToElement(jobCard).perform();

            // Görünen View Role butonunu seçin ve tıklayın
            WebElement viewRoleButton = driver.findElement(By.xpath("//*[@id=\"jobs-list\"]/div[1]/div/a"));
            viewRoleButton.click();
    }




    public void verifyRedirectedURLInNewTab() {
        // Mevcut pencerenin handle'ını al
        String mainWindow = driver.getWindowHandle();


        // Tüm pencere handle'larını al
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                // Yeni pencereye geçiş yap
                driver.switchTo().window(handle);

                // Yeni pencerenin URL'sini al
                String currentURL = driver.getCurrentUrl();

                // Beklenen URL'nin başlangıç kısmını tanımla
                String expectedURLStart = "https://jobs.lever.co/useinsider";

                // URL kontrolü yap
                if (currentURL.startsWith(expectedURLStart)) {
                    System.out.println("Redirected to the correct URL: " + currentURL);
                } else {
                    System.out.println("URL is incorrect! Actual URL: " + currentURL);
                }

                // Testin başarısını doğrula
                Assert.assertTrue(currentURL.startsWith(expectedURLStart), "The redirected URL is incorrect!");

                // İşlemler tamamlandıktan sonra yeni pencereyi kapat
                driver.close();

                // Ana pencereye geri dön
                driver.switchTo().window(mainWindow);
            }
        }
    }




}
