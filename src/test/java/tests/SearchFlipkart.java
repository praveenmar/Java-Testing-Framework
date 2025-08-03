package tests;

import framework.pages.GoogleSearchPage;
import org.testng.annotations.Test;
import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchFlipkart extends BaseTest {
    
    @Test(groups = {"smoke"}) 
    public void SearchinGoogle() {
        System.out.println("[DEBUG] Test method SearchinGoogle called");
        System.out.println("[DEBUG] Driver is null: " + (driver == null));
        
        if (driver == null) {
            throw new RuntimeException("WebDriver is null! Driver was not initialized properly. @BeforeMethod was not called.");
        }
        
        System.out.println("[DEBUG] Driver class: " + driver.getClass().getName());
        driver.get("https://www.google.com");
        // Wait for the search box to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        GoogleSearchPage googleSearchPage = new GoogleSearchPage(driver);
        googleSearchPage.searchFor("flipkart");
        
    }







    
} 