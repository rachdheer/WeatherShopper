package weathershopper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class WS_HomePage 
{
	WebDriver driver;
	public WS_HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	By weather =  By.xpath("//span[@id='temperature']");
	By moisturizers = By.xpath("//button[contains(text(),'Buy moisturizers')]");
	By sunscreens = By.xpath("//button[contains(text(),'Buy sunscreens')]");
	
	
	public WebElement temperature() {
		 return driver.findElement(weather);
	}
	
	public String getTemperatureValue(String value) {
		String getTemp = "";

		int getlen = value.length();
		if(getlen > 3) {
			 getTemp = value.substring(0,2);
		} else {
			  getTemp = value.substring(0,1);
		}
		return getTemp;
	}
	
	public WebElement moisturizer() {
		return driver.findElement(moisturizers);
	}
	
	public WebElement sunscreens() {
		return driver.findElement(sunscreens);
	}
	
	
	
}
