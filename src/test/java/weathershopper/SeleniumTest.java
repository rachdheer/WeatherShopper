package weathershopper;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.apache.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.*;



public class SeleniumTest extends TestBase{
	
	private static Logger log = LogManager.getLogger(SeleniumTest.class.getName());
	

	@Test
	public void WeatherShopperEndToEndAutomation() throws InterruptedException {
		
		getDriver().get("https://weathershopper.pythonanywhere.com/");
		log.debug("Get title====>"+getDriver().getTitle());
		String actualTitle = getDriver().getTitle();
		String expectedTitle = "Current Temperature";
		
		Assert.assertEquals(actualTitle,expectedTitle,"The expected website is not Launched");
		
		
		WS_HomePage homePage =  new WS_HomePage(getDriver());
		WS_CommonMethodsPage commonPage = new WS_CommonMethodsPage(getDriver());
		WS_CheckoutPage chkPage = new WS_CheckoutPage(getDriver());
		
		String getTemperatureText = homePage.temperature().getAttribute("textContent");
		System.out.println("the temperature is ===>"+getTemperatureText);
		
		String getTemValue = homePage.getTemperatureValue(getTemperatureText);
			
		int temperature = Integer.parseInt(getTemValue);
		System.out.println("converted Temp value ===>"+temperature);
	
		
		if(temperature < 19) {
			
			//click on Moisturiser button 
			homePage.moisturizer().click();
			waitForPageLoad();
			
			//Thread.sleep(5000);
			//Add Aloe product to CART and get the product name 
			int productOnePrice = commonPage.addProductsWithLowestPriceToCart("ALOE");
			
			//Add ALMOND product to CART and get the product name 
			int proudctTwoPrice = commonPage.addProductsWithLowestPriceToCart("ALMOND");
			
			//click on CART 
			commonPage.cart().click();
			waitForPageLoad();
			
			//Verify CART is filled with correct products
			int getTableCount = commonPage.verifyCart().size();
			Assert.assertEquals(getTableCount, 2,"The number of products added into CART are not same");
			
			//Verify CART Total Price is Correct
			int getTotalPrice = commonPage.cartTotalPrice();
			Assert.assertEquals(getTotalPrice, productOnePrice + proudctTwoPrice,"The Total Price is not same");
			
			
		}
		if(temperature > 34) {
			
			homePage.sunscreens().click();
			waitForPageLoad();
			
			//Thread.sleep(5000);
			
			int productOnePrice = commonPage.addProductsWithLowestPriceToCart("SPF-50");
			
			int proudctTwoPrice = commonPage.addProductsWithLowestPriceToCart("SPF-30");
			//click on CART 
			commonPage.cart().click();
			waitForPageLoad();
			
			//Verify CART is filled with correct products
			int getTableCount = commonPage.verifyCart().size();
			AssertJUnit.assertEquals(getTableCount, 2);
			
			//Verify CART Total Price is Correct
			int getTotalPrice = commonPage.cartTotalPrice();
			AssertJUnit.assertEquals(getTotalPrice, productOnePrice + proudctTwoPrice);
		}
		
		waitForPageLoad();
		
		chkPage.clickPayWithCard().click();
		Thread.sleep(5000);
		
		getDriver().switchTo().frame("stripe_checkout_app");
		
		boolean isDisplayed = chkPage.validateStripePopUp().isDisplayed();
		Assert.assertTrue(isDisplayed, "The Stripe.com is not opened");
		
		chkPage.enterEmailID().sendKeys("test@gmail.com");
		
		//enter 16 digit card number into 4 digits each
		for(int i=0; i<=4; i++) {
			chkPage.enterCardNumber().sendKeys("4242");
		}
		Thread.sleep(1000);
		String strExpiryDate = commonPage.getLocalDateAndFormatToString();
		chkPage.enterExpiryDate(strExpiryDate);
		chkPage.enterCVC().sendKeys("226");
		chkPage.enterZIPCode().sendKeys("220");
		chkPage.clickPay().click();
		getDriver().switchTo().parentFrame();
		String paymentSuccess= chkPage.validatePayment().getAttribute("textContent");
		
		if (paymentSuccess.toLowerCase().contains("success")) 
		{
			assertTrue(true,"The Payment is Succesfull");
		} else {
			
			assertFalse(false,"The Payment Failed");
		}
		
	}

	
}