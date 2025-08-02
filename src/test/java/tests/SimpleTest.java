package tests;

import org.testng.annotations.Test;
import base.BaseTest;

public class SimpleTest extends BaseTest {
    
    @Test(groups = {"smoke"})
    public void simpleTest() {
        System.out.println("[DEBUG] SimpleTest method called");
        System.out.println("[DEBUG] Driver is null: " + (driver == null));
        
        if (driver == null) {
            throw new RuntimeException("Driver is null in SimpleTest");
        }
        
        System.out.println("[DEBUG] Driver initialized successfully: " + driver.getClass().getName());
        driver.get("https://www.google.com");
        System.out.println("[DEBUG] Page title: " + driver.getTitle());
    }
} 