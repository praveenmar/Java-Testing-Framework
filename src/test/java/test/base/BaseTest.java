package test.base;

import framework.core.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseTest {
    protected WebDriver driver;
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private void log(String phase) {
        System.out.printf("[%s] [Thread-%d] %s%n",
                LocalTime.now().format(TIME_FORMAT),
                Thread.currentThread().getId(),
                phase);
    }

    @BeforeSuite
    public void beforeSuite() {
        log("@BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        log("@AfterSuite");
    }

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(@Optional("chrome") String browser) {
        log("@BeforeTest");
    }

    @AfterTest
    public void afterTest() {
        log("@AfterTest");
    }

    @BeforeClass
    public void beforeClass() {
        log("@BeforeClass");
    }

    @AfterClass
    public void afterClass() {
        log("@AfterClass");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        log("@BeforeMethod - Browser: " + browser);
        System.out.println("[DEBUG] BeforeMethod called with browser: " + browser);
        try {
            driver = DriverFactory.getDriver(browser);
            if (driver == null) {
                throw new RuntimeException("Failed to initialize WebDriver for browser: " + browser);
            }
            System.out.println("[DEBUG] WebDriver initialized successfully: " + driver.getClass().getName());
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to initialize WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        log("@AfterMethod");
        DriverFactory.quitDriver();
    }
} 