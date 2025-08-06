# TestNG Listeners Complete Guide

## Table of Contents
1. [What are TestNG Listeners?](#what-are-testng-listeners)
2. [Listener Registration Methods](#listener-registration-methods)
3. [When Listeners Are Invoked](#when-listeners-are-invoked)
4. [ITestResult vs ITestContext](#itestresult-vs-itestcontext)
5. [Common Use Cases](#common-use-cases)
6. [Best Practices](#best-practices)
7. [Troubleshooting](#troubleshooting)

---

## What are TestNG Listeners?

TestNG Listeners are **event handlers** that get notified when specific test execution events occur. They allow you to **hook into the test lifecycle** and perform custom actions.

### Key Benefits:
- ✅ **Separation of Concerns** - Test logic separate from cross-cutting concerns
- ✅ **Reusability** - Same listener can be used across multiple test classes
- ✅ **Centralized Control** - All test events handled in one place
- ✅ **Enhanced Reporting** - Custom metrics and logging
- ✅ **Automation Integration** - Screenshots, notifications, external system integration

---

## Listener Registration Methods

### 1. Global Registration (testng.xml) - Most Common

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="MySuite">
    <listeners>
        <listener class-name="listeners.TestListener"/>
    </listeners>
    <test name="MyTest">
        <classes>
            <class name="tests.OrangeHRMLoginTest"/>
        </classes>
    </test>
</suite>
```

**✅ Pros:**
- Works for **all test classes** in the suite
- **No code changes** needed in test classes
- **Centralized configuration**

**❌ Cons:**
- Only works when running via `testng.xml`
- **Doesn't work** when running individual tests with `-Dtest=ClassName`

### 2. Class-Level Registration (@Listeners) - Individual Control

```java
import org.testng.annotations.Listeners;
import listeners.TestListener;

@Listeners(TestListener.class)
public class OrangeHRMLoginTest extends BaseTest {
    // Your test methods
}
```

**✅ Pros:**
- Works for **individual test classes**
- Works when running with `-Dtest=ClassName`
- **Explicit and clear** which classes use the listener

**❌ Cons:**
- Need to add annotation to **each test class**
- **Code duplication** if many test classes

### 3. Programmatic Registration

```java
import org.testng.TestNG;
import java.util.Arrays;

TestNG testng = new TestNG();
testng.setListenerClasses(Arrays.asList(TestListener.class));
testng.setTestClasses(new Class[]{OrangeHRMLoginTest.class});
testng.run();
```

### 4. Base Class Approach (Recommended)

```java
@Listeners(TestListener.class)
public abstract class BaseTestWithListener extends BaseTest {
    // All test classes extend this instead of BaseTest
}

public class OrangeHRMLoginTest extends BaseTestWithListener {
    // Automatically gets listener functionality
}
```

---

## When Listeners Are Invoked

### Test Lifecycle Events (in order):

```
🚀 onStart(ITestContext)           // Before any tests in suite start
🔥 onTestStart(ITestResult)        // Before each @Test method
✅ onTestSuccess(ITestResult)      // After successful test
❌ onTestFailure(ITestResult)      // After failed test  
⏭️ onTestSkipped(ITestResult)     // After skipped test
🏁 onFinish(ITestContext)         // After all tests in suite complete
```

### Detailed Invocation Scenarios:

#### 1. **onStart(ITestContext)**
- **When:** Before any test methods in the suite start
- **Frequency:** Once per test suite
- **Use Cases:**
  - Set up test environment
  - Initialize test data
  - Start external services
  - Log suite start information

#### 2. **onTestStart(ITestResult)**
- **When:** Before each individual @Test method
- **Frequency:** Once per test method
- **Use Cases:**
  - Log test start information
  - Set test-specific data
  - Initialize test context
  - Record start time

#### 3. **onTestSuccess(ITestResult)**
- **When:** After a test method passes successfully
- **Frequency:** Once per successful test
- **Use Cases:**
  - Log success metrics
  - Take screenshots of successful states
  - Record performance data
  - Send success notifications

#### 4. **onTestFailure(ITestResult)**
- **When:** After a test method fails
- **Frequency:** Once per failed test
- **Use Cases:**
  - Take failure screenshots
  - Log detailed error information
  - Send failure notifications
  - Create bug reports
  - Record failure patterns

#### 5. **onTestSkipped(ITestResult)**
- **When:** After a test method is skipped
- **Frequency:** Once per skipped test
- **Skip Reasons:**
  - @Test(enabled = false)
  - Groups not included in execution
  - Dependencies not met
  - Manual skip conditions

#### 6. **onFinish(ITestContext)**
- **When:** After all test methods in the suite complete
- **Frequency:** Once per test suite
- **Use Cases:**
  - Generate summary reports
  - Clean up test environment
  - Send final notifications
  - Archive test artifacts
  - Record suite metrics

---

## ITestResult vs ITestContext

### ITestResult (Individual Test)
Represents a **single test method execution**:

```java
// Available Properties
result.getName()                    // Test method name
result.getTestClass().getName()     // Test class name
result.getMethod().getMethodName()  // Method name
result.getParameters()              // Test parameters
result.getStartMillis()             // Test start time
result.getEndMillis()               // Test end time
result.getThrowable()               // Exception if test failed
result.getAttribute("key")          // Custom attributes
result.getStatus()                  // Test status (SUCCESS, FAILURE, SKIP)
```

### ITestContext (Entire Suite)
Represents the **entire test suite context**:

```java
// Available Properties
context.getName()                   // Suite/test name
context.getAllTestMethods()         // All test methods in suite
context.getPassedTests()            // Passed tests collection
context.getFailedTests()            // Failed tests collection
context.getSkippedTests()           // Skipped tests collection
context.getCurrentXmlTest()         // Current XML test configuration
context.getSuite()                  // Suite information
context.getOutputDirectory()        // Output directory for reports
```

---

## Common Use Cases

### 1. Screenshot Capture
```java
@Override
public void onTestFailure(ITestResult result) {
    try {
        WebDriver driver = (WebDriver) result.getTestContext()
            .getAttribute("driver");
        if (driver != null) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshots/" + 
                result.getName() + "_" + System.currentTimeMillis() + ".png"));
        }
    } catch (Exception e) {
        System.out.println("Failed to capture screenshot: " + e.getMessage());
    }
}
```

### 2. Performance Monitoring
```java
@Override
public void onTestStart(ITestResult result) {
    result.setAttribute("startTime", System.currentTimeMillis());
}

@Override
public void onTestSuccess(ITestResult result) {
    long startTime = (Long) result.getAttribute("startTime");
    long duration = System.currentTimeMillis() - startTime;
    
    if (duration > 5000) { // 5 seconds threshold
        System.out.println("⚠️ Slow test detected: " + result.getName());
    }
}
```

### 3. External System Integration
```java
@Override
public void onTestFailure(ITestResult result) {
    // Send notification to Slack/Teams
    sendSlackNotification("Test failed: " + result.getName());
    
    // Log to external monitoring system
    logToMonitoringSystem(result.getName(), "FAILED", result.getThrowable());
    
    // Create JIRA ticket for failed tests
    createJiraTicket(result.getName(), result.getThrowable().getMessage());
}
```

### 4. Database State Management
```java
@Override
public void onTestStart(ITestResult result) {
    // Reset database to known state before each test
    DatabaseManager.resetTestData();
}

@Override
public void onFinish(ITestContext context) {
    // Clean up test data after all tests
    DatabaseManager.cleanupTestData();
}
```

### 5. Custom Reporting
```java
@Override
public void onTestSuccess(ITestResult result) {
    // Add custom metrics to report
    result.setAttribute("customMetric", "value");
    
    // Log to custom reporting system
    CustomReporter.logSuccess(result.getName(), result.getParameters());
}

@Override
public void onFinish(ITestContext context) {
    // Generate custom summary report
    generateCustomReport(context);
}
```

---

## Best Practices

### 1. **Use Both Registration Methods**
```java
// In testng.xml for suite runs
<listeners>
    <listener class-name="listeners.TestListener"/>
</listeners>

// In test classes for individual runs
@Listeners(TestListener.class)
public class YourTestClass { }
```

### 2. **Create Reusable Base Classes**
```java
@Listeners(TestListener.class)
public abstract class BaseTestWithListener extends BaseTest {
    // Common functionality for all test classes
}
```

### 3. **Handle Exceptions Gracefully**
```java
@Override
public void onTestFailure(ITestResult result) {
    try {
        // Your failure handling logic
        takeScreenshot(result.getName());
        sendNotification(result.getName());
    } catch (Exception e) {
        System.out.println("Listener error: " + e.getMessage());
        // Don't let listener errors affect test execution
    }
}
```

### 4. **Use Descriptive Logging**
```java
@Override
public void onTestStart(ITestResult result) {
    System.out.println("🔥 [LISTENER] Test started: " + result.getName());
    System.out.println("🔥 [LISTENER] Test class: " + result.getTestClass().getName());
    System.out.println("🔥 [LISTENER] Test method: " + result.getMethod().getMethodName());
}
```

### 5. **Keep Listeners Lightweight**
- Avoid heavy operations in listeners
- Use async operations for external calls
- Don't block test execution

---

## Troubleshooting

### Common Issues:

#### 1. **Listener Not Being Called**
**Problem:** Listener methods not executing
**Solutions:**
- Check if using `@Listeners` annotation
- Verify listener class is in correct package
- Ensure listener implements correct interface

#### 2. **Listener Only Works with testng.xml**
**Problem:** Listener works in suite but not individual tests
**Solution:** Add `@Listeners(TestListener.class)` to test classes

#### 3. **Listener Output Not Visible**
**Problem:** Listener messages not showing in console
**Solutions:**
- Use `System.out.println()` instead of logging frameworks
- Check for output buffering
- Verify listener is actually being called

#### 4. **Performance Issues**
**Problem:** Tests running slowly due to listener
**Solutions:**
- Move heavy operations to async threads
- Use conditional logic to skip expensive operations
- Profile listener performance

### Debug Checklist:
- [ ] Listener class implements ITestListener
- [ ] Listener registered in testng.xml or @Listeners annotation
- [ ] Listener class is in correct package
- [ ] No compilation errors in listener class
- [ ] Test execution includes listener registration

---

## Summary

TestNG Listeners are powerful tools for:
- **Test lifecycle monitoring**
- **Custom reporting and logging**
- **External system integration**
- **Screenshot capture and error handling**
- **Performance monitoring**
- **Test environment management**

Choose the registration method based on your needs:
- **testng.xml** for suite-wide listeners
- **@Listeners** for individual test classes
- **Both** for maximum compatibility

Remember to handle exceptions gracefully and keep listeners lightweight for optimal performance. 