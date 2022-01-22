package weathershopper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WS_CheckoutPage {
	
	WebDriver driver;
	public WS_CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}
	
	By payWithCardBTN = By.xpath("//button[@type='submit']");
	By stripePopUp    = By.xpath("//h1");
	By emailIDTXT 	  = By.xpath("//*[@id='email']");
	By cardNumberTXT  = By.xpath("//input[@id='card_number']");
	By expiryDateTXT  = By.xpath("//input[@id='cc-exp']");
	By cvcNumberTXT   = By.xpath("//input[@id='cc-csc']");
	By payMoneyBTN    = By.xpath("//button[@id='submitButton']");
	By zipCode 		  = By.xpath("//input[@id='billing-zip']");
	By paymentSuccess = By.xpath("//h2");
	
	public WebElement clickPayWithCard() 
	{
		System.out.println("Clicking pay with card");
		return driver.findElement(payWithCardBTN);
	}
	
	public WebElement validateStripePopUp()
	{
		System.out.println("Stripe com");
		return driver.findElement(stripePopUp);
	}
	
	public WebElement enterEmailID() {
		return driver.findElement(emailIDTXT);
	}
	
	public WebElement enterCardNumber() {
		return driver.findElement(cardNumberTXT);
	}

	public void enterExpiryDate(String strExpiry) {
		//return driver.findElement(expiryDateTXT);
		JavascriptExecutor j = (JavascriptExecutor)driver;
	     j.executeScript ("document.getElementById('cc-exp').value='"+strExpiry+"'");
	}
	
	public WebElement enterCVC() {
		return driver.findElement(cvcNumberTXT);
	}
	
	public WebElement clickPay() {
		return driver.findElement(payMoneyBTN);
	}
	
	public WebElement enterZIPCode() {
		return driver.findElement(zipCode);
	}
	
	public  WebElement validatePayment() {
		
		return driver.findElement(paymentSuccess);
		
	}
	
	
}
