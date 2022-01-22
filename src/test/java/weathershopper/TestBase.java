package weathershopper;

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
	
	//public static WebDriver driver;
	public ThreadLocal<WebDriver> driver =  new ThreadLocal<>();
	
	
	public void setDriver(WebDriver driver) {
		this.driver.set(driver);
	}
	
	public WebDriver getDriver() {
		return this.driver.get();
	}
	
	

    @BeforeMethod
    @Parameters("browser")
	public void initiateBrowser(String browser) {
    	
    	System.out.println("this is before method launching");
    	
       //Getting the Operating System 
    	String os = System.getProperty("os.name").toLowerCase();
    	
    	System.out.println("The operating system is :"+os);
    	
    	 //creating the driver here
    	
			if(os.contains("mac")) {
				//This is for MAC
				//Check if parameter passed from TestNG is 'firefox'
				if (browser.equalsIgnoreCase("chrome")){
						System.out.println("This browser is :"+browser);
						//set path to chromedriver.exe
						System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/WebDrivers/chromedriver");
						setDriver(new ChromeDriver());	
				} //Check if parameter passed as 'chrome'
				else if(browser.equalsIgnoreCase("firefox")){
					System.out.println("This browser is :"+browser);
					//Create FireFox Instance 
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/WebDrivers/geckodriver");
					setDriver(new FirefoxDriver());	
				}	
					
			} else {
				//this is for Windows
				//Check if parameter passed from TestNG is 'firefox'
				if(browser.equalsIgnoreCase("firefox")){
					//Create FireFox Instance 
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/WebDrivers/geckodriver.exe");
					setDriver(new FirefoxDriver());
				} //Check if parameter passed as 'chrome'
				else if(browser.equalsIgnoreCase("chrome")){
					//set path to chromedriver.exe
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/WebDrivers/chromedriver.exe");
					setDriver(new ChromeDriver());	
				}	
			}
		//getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		waitForPageLoad();
    }

    
    @AfterMethod
	public void teardown() {
    	System.out.println("this is after method closing");
        //quit the driver here
    	getDriver().close();
    	//getDriver().quit();
    }
    
    public void waitForPageLoad() {
    	
    	ExpectedCondition<Boolean> expectation = new 
    			ExpectedCondition<Boolean>() {
    				public Boolean apply(WebDriver driver) {
    					return ((JavascriptExecutor) driver)
    							.executeScript("return document.readtStatte")
    								.toString()
    									.equals("complete");
    				}
    			};
    	
    	try {
    		
    		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
    				.withTimeout(90,TimeUnit.SECONDS).pollingEvery(5,TimeUnit.SECONDS)
    				.ignoring(NoSuchElementException.class,StaleElementReferenceException.class);
    		
    		wait.until(expectation);
    		
    	} catch (Throwable error) {
    		
    	}
    }

}
