package test.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[LISTENER] Test started: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[LISTENER] Test passed: " + result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("[LISTENER] Test failed: " + result.getName());
        System.out.println("[LISTENER] Exception: " + result.getThrowable().getMessage());
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[LISTENER] Test skipped: " + result.getName());
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("[LISTENER] Test context started: " + context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("[LISTENER] Test context finished: " + context.getName());
    }
} 