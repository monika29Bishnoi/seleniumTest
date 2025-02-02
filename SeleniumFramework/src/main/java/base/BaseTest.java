package base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.github.dockerjava.transport.DockerHttpClient.Request.Method;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Constants;

public class BaseTest {


   public static WebDriver driver; 
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	@BeforeTest
	public void beforeTestMethod() {
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+File.separator+"reports"+File.separator+"SDETADDAExtentReport.html");	
		extent=new 	ExtentReports();
		extent.attachReporter(sparkReporter);
		sparkReporter.config().setTheme(Theme.DARK);
		extent.setSystemInfo("HostName", "RHEL8");
		extent.setSystemInfo("UserName", "root");
		sparkReporter.config().setDocumentTitle("Automation Reports");
		sparkReporter.config().setReportName("Automation Tests Results by SDET ADDA");
	}
	@BeforeMethod
	@org.testng.annotations.Parameters("browser")
	public void beforeMethodMethod(String browser) {
		//logger=extent.createTest(testMethod.name());
		setupDriver(browser);
		driver.manage().window().maximize();
		driver.get(Constants.url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
	}
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			logger.log(Status.FAIL,MarkupHelper.createLabel(result.getName()+" - Test Case Failed",ExtentColor.RED));
			logger.log(Status.FAIL,MarkupHelper.createLabel(result.getThrowable()+" - Test Case Failed",ExtentColor.RED));
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			logger.log(Status.SKIP,MarkupHelper.createLabel(result.getName()+" - Test Case Skiped",ExtentColor.ORANGE));
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			logger.log(Status.PASS,MarkupHelper.createLabel(result.getName()+" - Test Case Pass",ExtentColor.GREEN));
			driver.quit();
		}
	}
	@AfterTest
	public void afterTest()
	{
		extent.flush();
	}

	public void setupDriver(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			 ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				driver = new ChromeDriver(options);
				
			driver = new ChromeDriver();}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();}
	}

}
