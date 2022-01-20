package weathershopper;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class TestBase {
	
	public static WebDriver driver;
	
    @BeforeMethod
	public void init() {
        //creating the driver here
    	System.out.println("this is before method launching");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		waitForPageLoad();
    }
    
    @AfterMethod
	public void teardown() {
    	System.out.println("this is after method closing");
        //quit the driver here
    	driver.close();
    	driver.quit();
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
    		
    		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
    				.withTimeout(90,TimeUnit.SECONDS).pollingEvery(5,TimeUnit.SECONDS)
    				.ignoring(NoSuchElementException.class,StaleElementReferenceException.class);
    		
    		wait.until(expectation);
    		
    	} catch (Throwable error) {
    		
    	}
    }

}
