package test.tests;

import framework.pages.GoogleSearchPage;
import org.testng.annotations.Test;
import test.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchFlipkart2 extends BaseTest {
    @Test(groups = {"regression"}) 
    public void SearchinGoogle() {
        driver.get("https://www.google.com");
        // Wait for the search box to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        GoogleSearchPage googleSearchPage = new GoogleSearchPage(driver);
        googleSearchPage.searchFor("flipkart");
    }
} 