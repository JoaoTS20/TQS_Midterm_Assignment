import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class WebDriverSteps {
    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String arg0){
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1552, 840));
    }

    @And("I select {string}")
    public void iSelect(String arg0) {
        driver.findElement(By.id("type_form")).click();
        {
            WebElement dropdown = driver.findElement(By.id("type_form"));
            dropdown.findElement(By.xpath("//option[. = 'Aveiro']")).click();
        }
    }

    @And("I click in  {string}")
    public void iClickIn(String arg0) {
        driver.findElement(By.id("type_form")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();

    }

    @Then("the page title should start with {string}")
    public void thePageTitleShouldStartWith(String arg0) {
        assertThat(driver.getTitle(),
                containsString("Aveiro"));
        driver.close();
    }

    @After()
    public void closeBrowser() {

        driver.quit();
    }
}
