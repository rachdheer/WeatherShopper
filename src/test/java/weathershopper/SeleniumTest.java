package weathershopper;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.apache.log4j.LogManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.*;



public class SeleniumTest extends TestBase{
	
	private static Logger log = LogManager.getLogger(SeleniumTest.class.getName());
	

	@Test
	public void WeatherShopperEndToEndAutomation() throws InterruptedException {
		
		Reporter.log("Launching the Web Application");
		getDriver().get("https://weathershopper.pythonanywhere.com/"); 							//Launching the Web application
		Reporter.log("The title of the application is ====>"+getDriver().getTitle());			//logs
		
		String actualTitle = getDriver().getTitle();					//getting the title from the Web application 
		String expectedTitle = "Current Temperature";					//expected title 
		
		
		//Validating the expected title with actual title from the application
		Assert.assertEquals(actualTitle,expectedTitle,"The expected website is not Launched");
		
		
		
		WS_HomePage homePage =  new WS_HomePage(getDriver());
		WS_CommonMethodsPage commonPage = new WS_CommonMethodsPage(getDriver());
		WS_CheckoutPage chkPage = new WS_CheckoutPage(getDriver());
		
		Reporter.log("Getting the temperature string value from the application");
		String getTemperatureText = homePage.temperature().getAttribute("textContent"); //complete Temperature String
		
		String getTemValue = homePage.getTemperatureValue(getTemperatureText);         //get the temperature value 
		int temperature = Integer.parseInt(getTemValue);								//convert String to Int
		Reporter.log("converted Temp value to int is ===>"+temperature);
	
		
		//Validate if Temperature value is >19
		
		if(temperature < 19) {	
			Reporter.log("The temperature is below 19 and we are going to shop for Moisturozers");
			
			//click on Moisturiser button 
			homePage.moisturizer().click();
			waitForPageLoad();
			
			//Add Aloe product to CART and get the product name 
			int productOnePrice = commonPage.addProductsWithLowestPriceToCart("ALOE");
			
			//Add ALMOND product to CART and get the product name 
			int proudctTwoPrice = commonPage.addProductsWithLowestPriceToCart("ALMOND");
			
			//click on CART 
			commonPage.cart().click();
			waitForPageLoad();
			
			//Verify CART is filled with correct products
			int getTableCount = commonPage.verifyCart().size();
			Reporter.log("The total number of products added to cart is :"+getTableCount);
			Assert.assertEquals(getTableCount, 2,"The number of products added into CART are not same");
			
			//Verify CART Total Price is Correct
			int getTotalPrice = commonPage.cartTotalPrice();
			Reporter.log("The total cost of the Products is :"+getTotalPrice);
			Assert.assertEquals(getTotalPrice, productOnePrice + proudctTwoPrice,"The Total Price is not same");
			
			
		} else if(temperature > 34) {							//Validate if Temperature value is >34
			Reporter.log("The temperature is above 34 and we are going to shop for Suncreens");
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
			Reporter.log("The total number of products added to cart is :"+getTableCount);
			Assert.assertEquals(getTableCount, 2);
			
			//Verify CART Total Price is Correct
			int getTotalPrice = commonPage.cartTotalPrice();
			Reporter.log("The total cost of the Products is :"+getTotalPrice);
			Assert.assertEquals(getTotalPrice, productOnePrice + proudctTwoPrice);
			
		} else {	
			//failing the script if Temperature value is between 19 to 33
			Reporter.log("The temperature is between 20 to 33 so nothing to shop");
			assertTrue(false,"The temperature is between 20 to 33");
		}
		
		
		waitForPageLoad();														//wait for page load
		
		Reporter.log("click the PaywithCard button after addding th eproducts to CART ");
		chkPage.clickPayWithCard().click();										//click the PaywithCard
		
		Reporter.log("Switch to Stripe pop up frame");
		
		WebDriverWait stripFrame = new WebDriverWait(getDriver(), 3000);    
		stripFrame.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("stripe_checkout_app"));
		//getDriver().switchTo().frame("stripe_checkout_app");
	
		Reporter.log("Entering the email ID");
		chkPage.enterEmailID().sendKeys("test@gmail.com");
		
		Reporter.log("Entering the Card Niumber ");
		//enter 16 digit card number into 4 digits each
		for(int i=0; i<=4; i++) {
			chkPage.enterCardNumber().sendKeys("4242");
		}
		
		Reporter.log("Getting the Local date from the System and format to string with 1 year ahead");
		String strExpiryDate = commonPage.getLocalDateAndFormatToString();
		Reporter.log("Enetring Expiry Date");
		chkPage.enterExpiryDate(strExpiryDate);
		Reporter.log("Enetring CVV");
		chkPage.enterCVC().sendKeys("226");
		Reporter.log("Enetring ZIP Code");
		chkPage.enterZIPCode().sendKeys("220");
		Reporter.log("Click Payment");
		chkPage.clickPay().click();
		Thread.sleep(5000);
		Reporter.log("Validating Payment");
		getDriver().switchTo().parentFrame();
		String paymentSuccess= chkPage.validatePayment().getAttribute("textContent");
		Reporter.log("The payment information is :"+paymentSuccess);
		
		if (paymentSuccess.toLowerCase().contains("success")) 
		{
			Reporter.log("Payment is Succesfull");
			assertTrue(true,"The Payment is Succesfull");
		} else {
			Reporter.log("Payment Failed ");
			assertTrue(false,"The Payment Failed");
		}
		
	}

	
}