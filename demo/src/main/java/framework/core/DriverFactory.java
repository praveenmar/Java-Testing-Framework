package framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Returns the WebDriver instance for the current thread
    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
            } else {
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
            }
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    // Overloaded for backward compatibility (defaults to Chrome)
    public static WebDriver getDriver() {
        return getDriver("chrome");
    }

    // Quits the driver and cleans up resources for the current thread
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
} 