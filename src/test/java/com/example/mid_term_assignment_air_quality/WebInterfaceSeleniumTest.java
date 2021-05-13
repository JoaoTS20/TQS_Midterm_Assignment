package com.example.mid_term_assignment_air_quality;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

 class WebInterfaceSeleniumTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    @Test
     void WebInterfaceTest() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1376, 744));
        driver.findElement(By.id("type_form")).click();
        {
            WebElement dropdown = driver.findElement(By.id("type_form"));
            dropdown.findElement(By.xpath("//option[. = 'Aveiro']")).click();
        }
        driver.findElement(By.id("type_form")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.getTitle(),
                containsString("Aveiro"));
        driver.close();
    }
}
