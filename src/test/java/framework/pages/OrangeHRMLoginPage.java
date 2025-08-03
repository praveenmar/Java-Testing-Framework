package framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * OrangeHRM Login Page Object
 * This class encapsulates all the elements and actions related to the OrangeHRM login page
 * Following Page Object Model (POM) design pattern
 * 
 * Page URL: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
 */
public class OrangeHRMLoginPage {
    
    // WebDriver instance - passed from test classes
    private WebDriver driver;
    
    // WebDriverWait for explicit waits
    private WebDriverWait wait;
    
    // Page URL for navigation
    private static final String LOGIN_PAGE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    
    // ========== PAGE ELEMENTS ==========
    
    /**
     * Username input field
     * Located by name attribute: "username"
     */
    @FindBy(name = "username")
    private WebElement usernameInput;
    
    /**
     * Password input field
     * Located by name attribute: "password"
     */
    @FindBy(name = "password")
    private WebElement passwordInput;
    
    /**
     * Login button
     * Located by XPath for button with type="submit"
     */
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    
    /**
     * OrangeHRM logo/branding
     * Located by XPath for the logo image
     */
    @FindBy(xpath = "//img[@alt='company-branding']")
    private WebElement companyLogo;
    
    /**
     * Login form title
     * Located by XPath for the login form heading
     */
    @FindBy(xpath = "//h5[@class='oxd-text oxd-text--h5 orangehrm-login-title']")
    private WebElement loginTitle;
    
    /**
     * Error message container
     * Located by XPath for error alerts
     */
    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    private WebElement errorMessage;
    
    /**
     * Forgot password link
     * Located by XPath for the forgot password link
     */
    @FindBy(xpath = "//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']")
    private WebElement forgotPasswordLink;
    
    /**
     * Username field label
     * Located by XPath for the username label
     */
    @FindBy(xpath = "//label[@for='username']")
    private WebElement usernameLabel;
    
    /**
     * Password field label
     * Located by XPath for the password label
     */
    @FindBy(xpath = "//label[@for='password']")
    private WebElement passwordLabel;
    
    // Alternative locators for better reliability
    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameInputAlt;
    
    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInputAlt;
    
    @FindBy(xpath = "//button[@type='submit'] | //button[contains(@class,'oxd-button')]")
    private WebElement loginButtonAlt;
    
    // ========== CONSTRUCTOR ==========
    
    /**
     * Constructor - initializes the page object
     * 
     * @param driver - WebDriver instance passed from test class
     */
    public OrangeHRMLoginPage(WebDriver driver) {
        this.driver = driver;
        // Initialize WebDriverWait with 10 seconds timeout
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Initialize PageFactory to find elements
        PageFactory.initElements(driver, this);
    }
    
    // ========== PAGE NAVIGATION METHODS ==========
    
    /**
     * Navigate to the OrangeHRM login page
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage navigateToLoginPage() {
        driver.get(LOGIN_PAGE_URL);
        return this; // Method chaining
    }
    
    /**
     * Get the current page title
     * 
     * @return String - page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get the current page URL
     * 
     * @return String - current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    // ========== ELEMENT VERIFICATION METHODS ==========
    
    /**
     * Check if login page is loaded
     * 
     * @return boolean - true if page is loaded
     */
    public boolean isLoginPageLoaded() {
        try {
            // Try primary locators first
            wait.until(ExpectedConditions.visibilityOf(usernameInput));
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            return true;
        } catch (Exception e) {
            try {
                // Try alternative locators if primary ones fail
                wait.until(ExpectedConditions.visibilityOf(usernameInputAlt));
                wait.until(ExpectedConditions.visibilityOf(passwordInputAlt));
                wait.until(ExpectedConditions.visibilityOf(loginButtonAlt));
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }
    
    /**
     * Check if company logo is displayed
     * 
     * @return boolean - true if logo is visible
     */
    public boolean isCompanyLogoDisplayed() {
        try {
            return companyLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get login form title text
     * 
     * @return String - login title text
     */
    public String getLoginTitle() {
        try {
            return loginTitle.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    // ========== INPUT METHODS ==========
    
    /**
     * Enter username in the username field
     * 
     * @param username - username to enter
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage enterUsername(String username) {
        try {
            // Try primary locator first
            wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
            usernameInput.clear();
            usernameInput.sendKeys(username);
        } catch (Exception e) {
            // Try alternative locator if primary fails
            wait.until(ExpectedConditions.elementToBeClickable(usernameInputAlt));
            usernameInputAlt.clear();
            usernameInputAlt.sendKeys(username);
        }
        return this; // Method chaining
    }
    
    /**
     * Enter password in the password field
     * 
     * @param password - password to enter
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage enterPassword(String password) {
        try {
            // Try primary locator first
            wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
            passwordInput.clear();
            passwordInput.sendKeys(password);
        } catch (Exception e) {
            // Try alternative locator if primary fails
            wait.until(ExpectedConditions.elementToBeClickable(passwordInputAlt));
            passwordInputAlt.clear();
            passwordInputAlt.sendKeys(password);
        }
        return this; // Method chaining
    }
    
    /**
     * Clear username field
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage clearUsername() {
        usernameInput.clear();
        return this;
    }
    
    /**
     * Clear password field
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage clearPassword() {
        passwordInput.clear();
        return this;
    }
    
    /**
     * Get the current value in username field
     * 
     * @return String - current username value
     */
    public String getUsernameValue() {
        return usernameInput.getAttribute("value");
    }
    
    /**
     * Get the current value in password field
     * 
     * @return String - current password value
     */
    public String getPasswordValue() {
        return passwordInput.getAttribute("value");
    }
    
    // ========== ACTION METHODS ==========
    
    /**
     * Click the login button
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage clickLoginButton() {
        // Wait for login button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        // Click the login button
        loginButton.click();
        return this; // Method chaining
    }
    
    /**
     * Click the forgot password link
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage clickForgotPasswordLink() {
        // Wait for forgot password link to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));
        // Click the forgot password link
        forgotPasswordLink.click();
        return this; // Method chaining
    }
    
    // ========== COMPOSITE METHODS ==========
    
    /**
     * Complete login process with username and password
     * This is a composite method that combines multiple actions
     * 
     * @param username - username to enter
     * @param password - password to enter
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage login(String username, String password) {
        // Enter username
        enterUsername(username);
        // Enter password
        enterPassword(password);
        // Click login button
        clickLoginButton();
        return this; // Method chaining
    }
    
    /**
     * Complete login process with method chaining
     * Alternative approach using method chaining
     * 
     * @param username - username to enter
     * @param password - password to enter
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage loginWithChaining(String username, String password) {
        return enterUsername(username)
               .enterPassword(password)
               .clickLoginButton();
    }
    
    // ========== VALIDATION METHODS ==========
    
    /**
     * Check if error message is displayed
     * 
     * @return boolean - true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            // Wait for error message to be visible
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get error message text
     * 
     * @return String - error message text
     */
    public String getErrorMessage() {
        try {
            // Wait for error message to be visible
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Check if username field is empty
     * 
     * @return boolean - true if username field is empty
     */
    public boolean isUsernameFieldEmpty() {
        return getUsernameValue().isEmpty();
    }
    
    /**
     * Check if password field is empty
     * 
     * @return boolean - true if password field is empty
     */
    public boolean isPasswordFieldEmpty() {
        return getPasswordValue().isEmpty();
    }
    
    /**
     * Check if login button is enabled
     * 
     * @return boolean - true if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Wait for page to load completely
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage waitForPageLoad() {
        // Wait for username input to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(org.openqa.selenium.By.name("username")));
        return this;
    }
    
    /**
     * Refresh the current page
     * 
     * @return OrangeHRMLoginPage instance for method chaining
     */
    public OrangeHRMLoginPage refreshPage() {
        driver.navigate().refresh();
        return this;
    }
    
    /**
     * Get field labels for validation
     * 
     * @return String array - [usernameLabel, passwordLabel]
     */
    public String[] getFieldLabels() {
        String usernameLabelText = usernameLabel.getText();
        String passwordLabelText = passwordLabel.getText();
        return new String[]{usernameLabelText, passwordLabelText};
    }
} 