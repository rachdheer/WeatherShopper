package weathershopper;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class TestBase {

	// public static WebDriver driver;
	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// Setting up the Driver
	public void setDriver(WebDriver driver) {
		this.driver.set(driver);
	}

	// This is to get the driver
	public WebDriver getDriver() {
		return this.driver.get();
	}

	// This Method will run first before the test begin
	@BeforeMethod
	@Parameters("browser") // this parameter is reading from XML file
	public void initiateBrowser(String browser) {

		Reporter.log("Getting the System Operating System ");

		// Getting the Operating System
		String os = System.getProperty("os.name").toLowerCase();

		Reporter.log("The operating system is :" + os);

		// creating the driver here

		if (os.contains("mac")) {
			// This is for MAC
			// Check if parameter passed from TestNG is 'firefox'
			if (browser.equalsIgnoreCase("chrome")) {
				Reporter.log("This MAC browser is :" + browser);
				// set path to chromedriver.exe
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/WebDrivers/chromedriver");
				setDriver(new ChromeDriver());
			} // Check if parameter passed as 'chrome'
			else if (browser.equalsIgnoreCase("firefox")) {
				Reporter.log("This MAC browser is :" + browser);
				// Create FireFox Instance
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/WebDrivers/geckodriver");
				setDriver(new FirefoxDriver());
			}

		} else {
			// this is for Windows
			// Check if parameter passed from TestNG is 'firefox'
			if (browser.equalsIgnoreCase("firefox")) {
				Reporter.log("This Windows browser is :" + browser);
				// Create FireFox Instance
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/WebDrivers/geckodriver.exe");
				setDriver(new FirefoxDriver());
			} // Check if parameter passed as 'chrome'
			else if (browser.equalsIgnoreCase("chrome")) {
				Reporter.log("This Windows browser is :" + browser);
				// set path to chromedriver.exe
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/WebDrivers/chromedriver.exe");
				setDriver(new ChromeDriver());
			}
		}
		// getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		waitForPageLoad();
	}

	@AfterMethod
	public void teardown() {
		Reporter.log("Closing the Browser");
		getDriver().close();
		// getDriver().quit();
	}

	public void waitForPageLoad() {

		Reporter.log("Waiting for the page to load");
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readtStatte").toString()
						.equals("complete");
			}
		};

		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(90, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);

			wait.until(expectation);

		} catch (Throwable error) {

		}
	}

}
