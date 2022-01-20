package weathershopper;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.annotations.Test;

import weathershopper.WS_CheckoutPage;
import weathershopper.WS_CommonMethodsPage;
import weathershopper.WS_HomePage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.*;



public class SeleniumTest extends TestBase{
	

	@Test
	public void BrowserAutomation() throws InterruptedException {
		
		driver.get("https://weathershopper.pythonanywhere.com/");
		System.out.println("Get title====>"+driver.getTitle());
		String title = driver.getTitle();
		
		Assert.assertTrue(title.equals("Current Temperature"), "The expected website is not Launched");
		

		
		WS_HomePage homePage =  new WS_HomePage(driver);
		WS_CommonMethodsPage commonPage = new WS_CommonMethodsPage(driver);
		WS_CheckoutPage chkPage = new WS_CheckoutPage(driver);
		
		String getTemperatureText = homePage.temperature().getAttribute("textContent");
		System.out.println("the temperature is ===>"+getTemperatureText);
		
		String getTemValue = homePage.getTemperatureValue(getTemperatureText);
			
		int temperature = Integer.parseInt(getTemValue);
		System.out.println("converted Temp value ===>"+temperature);
	
		
		if(temperature < 19) {
			//click on Moisturiser button 
			homePage.moisturizer().click();
			
			Thread.sleep(5000);
			//Add Aloe product to CART and get the product name 
			int productOnePrice = commonPage.addProductsWithLowestPriceToCart("ALOE");
			
			//Add ALMOND product to CART and get the product name 
			int proudctTwoPrice = commonPage.addProductsWithLowestPriceToCart("ALMOND");
			
			//click on CART 
			commonPage.cart().click();
			
			//Verify CART is filled with correct products
			int getTableCount = commonPage.verifyCart().size();
			AssertJUnit.assertEquals(getTableCount, 2);
			
			//Verify CART Total Price is Correct
			int getTotalPrice = commonPage.cartTotalPrice();
			AssertJUnit.assertEquals(getTotalPrice, productOnePrice + proudctTwoPrice);
			
			
		}
		if(temperature > 34) {
			homePage.sunscreens().click();
			
			Thread.sleep(5000);
			
			int productOnePrice = commonPage.addProductsWithLowestPriceToCart("SPF-50");
			
			int proudctTwoPrice = commonPage.addProductsWithLowestPriceToCart("SPF-30");
			//click on CART 
			commonPage.cart().click();
			
			//Verify CART is filled with correct products
			int getTableCount = commonPage.verifyCart().size();
			AssertJUnit.assertEquals(getTableCount, 2);
			
			//Verify CART Total Price is Correct
			int getTotalPrice = commonPage.cartTotalPrice();
			AssertJUnit.assertEquals(getTotalPrice, productOnePrice + proudctTwoPrice);
		}
		
		Thread.sleep(5000);
		
		chkPage.clickPayWithCard().click();
		Thread.sleep(5000);
		
		driver.switchTo().frame("stripe_checkout_app");
		
		boolean isDisplayed = chkPage.validateStripePopUp().isDisplayed();
		Assert.assertTrue(isDisplayed, "The Stripe.com is not opened");
		
		chkPage.enterEmailID().sendKeys("test@gmail.com");
		chkPage.enterCardNumber().sendKeys("4242424242424242");
	
		    
		    System.out.println(commonPage.getLocalDateAndFormatToString());
		
		//chkPage.enterExpiryDate(getDate().now)
		
		
		
		
	
		
		
	}
	
	
	
}