package tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import dataProvider.ConfigFileReader;

public class ApiTestBase {

	public static WebDriver driver = null;
	public static ExtentTest logger = null;
	public static ConfigFileReader configFileReader = null;
	ExtentReports extent;
	// ExtentTest logger;
	ExtentHtmlReporter htmlReporter;

	@BeforeSuite
	public void initialize() throws IOException {

		// extent report
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/test-output/Extentreport" + dateName + ".html");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "TestMachine");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User Name", "Anoop");

		htmlReporter.loadConfig("D:\\WorkSpace\\apiProject\\test-output\\extent-config.xml");
		String css = ".r-img{width: 50%;}";
		htmlReporter.config().setCSS(css);

		// Creating an object of ConfigFileReader
		configFileReader = new ConfigFileReader();

	}

	@BeforeMethod(alwaysRun = true)
	public void logging(Method method) throws IOException, InterruptedException {

		String descriptiveTestName = method.getAnnotation(Test.class).testName();
		String description = method.getAnnotation(Test.class).description();

		logger = extent.createTest(descriptiveTestName).assignCategory(description);

	}

	@AfterMethod(alwaysRun = true)
	public void screenShot(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			try {

				logger.log(Status.FAIL,
						MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				logger.log(Status.FAIL,
						MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));

			} catch (Exception e) {
				System.out.println("Exception while taking screenshot " + e.getMessage());
			}
		} else if (result.getStatus() == ITestResult.SKIP) {

			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));

		} else if (result.getStatus() == ITestResult.SUCCESS) {

			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));

		}
		extent.flush();
		// extent.endTest(logger);
		// driver.close();
	}

	@AfterTest(alwaysRun = true)
	public void endReport() {

		// flush() - to write or update test information to your report.
		extent.flush();
	}

	@AfterSuite
	// Test cleanup
	public void TeardownTest() {
		// TestBase.driver.quit();
	}

}
