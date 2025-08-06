package tests;

import framework.pages.OrangeHRMLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import listeners.TestListener;
import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.time.Duration;

/**
 * Test class for OrangeHRM Login functionality
 * Demonstrates how to use Page Object Model in test classes
 * Uses explicit waits for better reliability
 */
@Listeners(TestListener.class)
public class OrangeHRMLoginTest extends BaseTest {
    
    // Test data constants
    private static final String VALID_USERNAME = "Admin";
    private static final String VALID_PASSWORD = "admin123";
    private static final String INVALID_USERNAME = "InvalidUser";
    private static final String INVALID_PASSWORD = "InvalidPass";
    private static final String EMPTY_USERNAME = "";
    private static final String EMPTY_PASSWORD = "";
    
    /**
     * Test successful login with valid credentials
     * Demonstrates basic login functionality with explicit waits
     */
    @Test(priority = 1, groups = {"smoke", "login"})
    public void testSuccessfulLogin() {
        // Create OrangeHRM Login Page Object
        OrangeHRMLoginPage loginPage = new OrangeHRMLoginPage(driver);
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Create explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        
        // Wait for page to load
        wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        
        // Verify page is loaded
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page should be loaded");
        
        // Verify page title
        String pageTitle = loginPage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("OrangeHRM"), "Page title should contain OrangeHRM");
        
        // Verify company logo is displayed
        Assert.assertTrue(loginPage.isCompanyLogoDisplayed(), "Company logo should be displayed");
        
        // Wait for username field and perform login
        wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        
        // Wait for navigation to complete
        wait.until(ExpectedConditions.urlContains("dashboard"));
        
        // Verify successful login by checking URL change
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard"), "Should navigate to dashboard after successful login");
    }
    
    /**
     * Test login with invalid credentials
     * Demonstrates error handling and validation with explicit waits
     */
    // @Test(priority = 2, groups = {"login", "negative"})
    // public void testLoginWithInvalidCredentials() {
    //     // Create OrangeHRM Login Page Object
    //     OrangeHRMLoginPage loginPage = new OrangeHRMLoginPage(driver);
        
    //     // Navigate to login page
    //     loginPage.navigateToLoginPage();
        
    //     // Create explicit wait
    //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
    //     // Wait for page to load
    //     wait.until(ExpectedConditions.titleContains("OrangeHRM"));
        
    //     // Wait for username field and perform login with invalid credentials
    //     wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
    //     loginPage.login(INVALID_USERNAME, INVALID_PASSWORD);
        
    //     // Wait for error message to appear
    //     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")));
        
    //     // Verify error message is displayed
    //     Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid credentials");
        
    //     // Verify error message text
    //     String errorMessage = loginPage.getErrorMessage();
    //     Assert.assertFalse(errorMessage.isEmpty(), "Error message should not be empty");
        
    //     // Verify we're still on login page
    //     String currentUrl = loginPage.getCurrentUrl();
    //     Assert.assertTrue(currentUrl.contains("auth/login"), "Should remain on login page after failed login");
    // }
    
    
} 