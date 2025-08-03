package framework.core;

// Import required Selenium WebDriver classes
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
// Import WebDriverManager for automatic driver binary management
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverFactory class - Manages WebDriver instances for parallel test execution
 * Uses ThreadLocal to ensure each test thread gets its own WebDriver instance
 */
public class DriverFactory {
    
    // ThreadLocal ensures each thread gets its own WebDriver instance
    // This prevents conflicts when running tests in parallel
    // ThreadLocal<WebDriver> means each thread stores its own WebDriver object
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Returns the WebDriver instance for the current thread
     * Creates a new driver if one doesn't exist for this thread
     * 
     * @param browser - The browser type ("chrome" or "firefox")
     * @return WebDriver instance for the current thread
     */
    public static WebDriver getDriver(String browser) {
        // Check if current thread already has a WebDriver instance
        // driver.get() returns null if no driver exists for this thread
        if (driver.get() == null) {
            
            // If browser parameter is "firefox" (case-insensitive)
            if (browser.equalsIgnoreCase("firefox")) {
                // Setup Firefox driver binary automatically
                WebDriverManager.firefoxdriver().setup();
                // Create new FirefoxDriver and store it for current thread
                driver.set(new FirefoxDriver());
            } 
            else if(browser.equalsIgnoreCase("edge")){
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
            }
            else {
                // Default to Chrome (if browser is "chrome" or any other value)
                // Setup Chrome driver binary automatically
                WebDriverManager.chromedriver().setup();
                // Create new ChromeDriver and store it for current thread
                driver.set(new ChromeDriver());
            }
            
            // Maximize the browser window for the newly created driver
            // driver.get() gets the driver we just set for this thread
            driver.get().manage().window().maximize();
        }
        
        // Return the WebDriver instance for the current thread
        // This could be a newly created driver or an existing one
        return driver.get();
    }

    /**
     * Overloaded method for backward compatibility
     * Defaults to Chrome browser when no browser parameter is provided
     * 
     * @return WebDriver instance (Chrome by default)
     */
    public static WebDriver getDriver() {
        // Call the main getDriver method with "chrome" as default browser
        return getDriver("chrome");
    }

    /**
     * Quits the WebDriver and cleans up resources for the current thread
     * This method should be called after each test to free up resources
     */
    public static void quitDriver() {
        // Check if current thread has a WebDriver instance
        if (driver.get() != null) {
            // Quit the WebDriver (closes browser and releases resources)
            driver.get().quit();
            // Remove the WebDriver instance from ThreadLocal for this thread
            // This prevents memory leaks and allows garbage collection
            driver.remove();
        }
    }
} 