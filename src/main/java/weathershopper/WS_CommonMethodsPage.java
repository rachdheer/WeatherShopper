package weathershopper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class WS_CommonMethodsPage {
	
	
	WebDriver driver;
	public WS_CommonMethodsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	By cart				=  By.xpath("//button[@onclick='goToCart()']");
	By cartTable 		=  By.xpath("//table[@class= 'table table-striped']/tbody/tr");
	By cartTotalPrice 	=  By.xpath("//p[@id='total']");
	
	
	
	
	public int addProductsWithLowestPriceToCart(String strProduct) {
			
			List<WebElement> list_of_products = driver.findElements(By.xpath("//div[@class='text-center col-4']/p[1]"));
			List<WebElement> list_of_products_price = driver.findElements(By.xpath("//div[@class='text-center col-4']/p[2]"));
			
			//Use of HashMaop to store Products and Their prices(after conversion to Integer)
			String product_name;
			String product_price;
			int int_product_price;
			
			HashMap<Integer, String> map_final_products = new HashMap<Integer,String>();
			
			for(int i=0;i<list_of_products.size();i++) {
				
				product_name = list_of_products.get(i).getText();//Iterate and fetch product name
				System.out.println("Product name =====>"+product_name);
				
				if(product_name.toUpperCase().contains(strProduct)){
					
					System.out.println("Came in ********");
					product_price = list_of_products_price.get(i).getText();//Iterate and fetch product price
					
					product_price = product_price.replaceAll("[^0-9]", "");//Replace anything will space other than numbers
					
					int_product_price = Integer.parseInt(product_price);//Convert to Integer
					
					map_final_products.put(int_product_price,product_name);//Add product and price in HashMap
				}
				System.out.println("Came Out ********");
			}
			
			
			Reporter.log("Product Name and price fetched from UI and saved in HashMap as:" + map_final_products.toString() + "<br>");
	 
			//Get all the keys from Hash Map
			Set<Integer> allkeys = map_final_products.keySet();
			ArrayList<Integer> array_list_values_product_prices = new ArrayList<Integer>(allkeys);
			
			//Sort the Prices in Array List using Collections class
			//this will sort in ascending order lowest at the top and highest at the bottom
			Collections.sort(array_list_values_product_prices);
			
			//Highest Product is
			int high_price = array_list_values_product_prices.get(array_list_values_product_prices.size()-1);
			
			//Low price is
			int low_price = array_list_values_product_prices.get(0);
			int second_low_price = array_list_values_product_prices.get(1);
			
			//Reporter.log("High Product Price is: " + high_price + " Product name is: " + map_final_products.get(high_price),true);
			Reporter.log("Low Product Price is: " + low_price + " Product name is: " + map_final_products.get(low_price),true);
			//Reporter.log("Second Low Product Price is: " + second_low_price + " Product name is: " + map_final_products.get(second_low_price),true);
	
			//click on lowest price product add button
			String first_lowest_price = "(//button[contains(@onclick, '"+map_final_products.get(low_price)+"')])";
			driver.findElement(By.xpath(first_lowest_price)).click();
			
			//click on second lowest product add Button
			//String second_lowest_price = "(//button[contains(@onclick, '"+map_final_products.get(second_low_price)+"')])";
			//driver.findElement(By.xpath(second_lowest_price)).click();
			
			
			int getProductCost = low_price;
			
			return getProductCost;
			
				
					/*
					 * List<WebElement> myElements = driver.findElements(getProduct); for(WebElement
					 * e : myElements) { System.out.println(e.getText());
					 * if(e.getText().toUpperCase().contains("ALOE")) { HashMap<String, String>
					 * hshMap = new HashMap<String, String>(); hshMap.put(e.getText(),"");
					 * 
					 * } }
					 */
					 
			
		}

	public WebElement cart() {
		return driver.findElement(cart);
	}
	
	public List<WebElement> verifyCart() {
		
		List<WebElement> rows = driver.findElements(cartTable);
		return rows;
	}
	
	public int cartTotalPrice() {
		
		String total_price;
		String product_price;
		int int_product_price;
		
		total_price = driver.findElement(cartTotalPrice).getText();;//Get the Total Text
		
		product_price = total_price.replaceAll("[^0-9]", ""); //Replace everything with space other than numbers
		
		int_product_price = Integer.parseInt(product_price); //Convert to Integer
		
		return int_product_price;
		
	}
	
	public String getLocalDateAndFormatToString() {
		
		LocalDate date = LocalDate.now();
	    LocalDate datetime = date.plusYears(1);
	    DateTimeFormatter formatters1 = DateTimeFormatter.ofPattern("MM/uu");
	    String dateInString = datetime.format(formatters1);
	    return dateInString;
	}

}
