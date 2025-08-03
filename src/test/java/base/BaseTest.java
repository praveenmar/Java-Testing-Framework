package base;

// Import the DriverFactory to manage WebDriver instances
import framework.core.DriverFactory;
// Import Selenium WebDriver for browser automation
import org.openqa.selenium.WebDriver;
// Import TestNG annotations for test lifecycle management
import org.testng.annotations.*;
// Import Java time utilities for logging timestamps
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * BaseTest - Abstract base class that all test classes must extend
 * Provides common setup, teardown, and logging functionality
 * Manages WebDriver lifecycle using TestNG annotations
 */
public abstract class BaseTest {
    
    // Protected WebDriver instance that all child test classes can access
    // This driver is inherited by all test classes that extend BaseTest
    protected WebDriver driver;
    
    // Static constant for formatting timestamps in logs
    // Format: HH:mm:ss.SSS (e.g., 14:30:25.123)
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * Helper method to log test execution phases with timestamp and thread ID
     * This helps track which thread is executing which phase during parallel runs
     * 
     * @param phase - The test phase being logged (e.g., "@BeforeMethod", "@AfterMethod")
     */
    private void log(String phase) {
        // Print formatted log with timestamp, thread ID, and phase
        // Format: [14:30:25.123] [Thread-1] @BeforeMethod
        System.out.printf("[%s] [Thread-%d] %s%n",
                LocalTime.now().format(TIME_FORMAT),  // Current time
                Thread.currentThread().getId(),        // Current thread ID
                phase);                               // Test phase
    }

    /**
     * @BeforeSuite - Runs once before all tests in the suite
     * This is the highest level of TestNG lifecycle
     * Useful for one-time setup like database connections, test data preparation
     */
    @BeforeSuite
    public void beforeSuite() {
        log("@BeforeSuite");
    }

    /**
     * @AfterSuite - Runs once after all tests in the suite complete
     * This is the cleanup phase for the entire test suite
     * Useful for cleanup like closing database connections, generating reports
     */
    @AfterSuite
    public void afterSuite() {
        log("@AfterSuite");
    }

    /**
     * @BeforeTest - Runs before each <test> tag in testng.xml
     * If you have multiple <test> tags, this runs before each one
     * 
     * @param browser - Browser parameter from testng.xml or command line
     * @Optional("chrome") - Default value if no browser parameter is provided
     */
    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(@Optional("chrome") String browser) {
        log("@BeforeTest");
        // Note: Browser parameter is captured but not used here
        // It's used in setUp() method instead
    }

    /**
     * @AfterTest - Runs after each <test> tag in testng.xml
     * Cleanup phase for each test group
     */
    @AfterTest
    public void afterTest() {
        log("@AfterTest");
    }

    /**
     * @BeforeClass - Runs once before the first test method in each test class
     * If you have multiple test classes, this runs for each class
     */
    @BeforeClass
    public void beforeClass() {
        log("@BeforeClass");
    }

    /**
     * @AfterClass - Runs once after the last test method in each test class
     * Cleanup phase for each test class
     */
    @AfterClass
    public void afterClass() {
        log("@AfterClass");
    }

    /**
     * @BeforeMethod - Runs before each test method (@Test)
     * This is where WebDriver is initialized for each test
     * alwaysRun = true ensures this runs even if previous methods fail
     * 
     * @param browser - Browser parameter from testng.xml or command line
     * @Optional("chrome") - Default value if no browser parameter is provided
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        // Log the current test phase with browser information
        log("@BeforeMethod - Browser: " + browser);
        System.out.println("[DEBUG] BeforeMethod called with browser: " + browser);
        
        try {
            // Get WebDriver instance from DriverFactory for the current thread
            // This ensures each test thread gets its own WebDriver instance
            driver = DriverFactory.getDriver(browser);
            
            // Validate that WebDriver was successfully created
            if (driver == null) {
                throw new RuntimeException("Failed to initialize WebDriver for browser: " + browser);
            }
            
            // Log successful WebDriver initialization with driver class name
            System.out.println("[DEBUG] WebDriver initialized successfully: " + driver.getClass().getName());
            
        } catch (Exception e) {
            
            // Log any errors during WebDriver initialization
            System.err.println("[ERROR] Failed to initialize WebDriver: " + e.getMessage());
            e.printStackTrace();
            // Re-throw the exception to fail the test
            throw e;
        }
    }

    /**
     * @AfterMethod - Runs after each test method (@Test)
     * This is where WebDriver cleanup happens for each test
     * Ensures browser is closed and resources are freed
     */
    @AfterMethod
    public void tearDown() {
        log("@AfterMethod");
        // Quit the WebDriver and clean up ThreadLocal storage
        // This prevents memory leaks and ensures clean state for next test
        DriverFactory.quitDriver();
    }
} 