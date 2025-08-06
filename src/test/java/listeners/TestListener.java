package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🔥 [LISTENER] Test started: " + result.getName());
        System.out.println("🔥 [LISTENER] Test class: " + result.getTestClass().getName());
        System.out.println("🔥 [LISTENER] Test method: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ [LISTENER] Test passed: " + result.getName());
        System.out.println("✅ [LISTENER] Duration: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ [LISTENER] Test failed: " + result.getName());
        System.out.println("❌ [LISTENER] Exception: " + result.getThrowable().getMessage());
        System.out.println("❌ [LISTENER] Duration: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭️ [LISTENER] Test skipped: " + result.getName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("🚀 [LISTENER] Test suite started: " + context.getName());
        System.out.println("🚀 [LISTENER] Total tests: " + context.getAllTestMethods().length);
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 [LISTENER] Test suite finished: " + context.getName());
        System.out.println("🏁 [LISTENER] Passed: " + context.getPassedTests().size());
        System.out.println("🏁 [LISTENER] Failed: " + context.getFailedTests().size());
        System.out.println("🏁 [LISTENER] Skipped: " + context.getSkippedTests().size());
    }
} 