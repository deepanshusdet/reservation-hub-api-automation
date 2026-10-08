package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class ExtentListener implements ITestListener {

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest = ExtentManager.getReport()
                .createTest(result.getMethod().getMethodName());

        test.set(extentTest);

        ApiLogStore.clear();
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().info(
                "<b>Request / Response Details</b><br>"
                + ApiLogStore.get());

        test.get().pass("Assertions Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().info(
                "<b>Request / Response Details</b><br>"
                + ApiLogStore.get());

        test.get().fail("Assertions Failed");
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentManager.getReport().flush();
    }
}