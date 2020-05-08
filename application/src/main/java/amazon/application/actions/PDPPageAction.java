package amazon.application.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.relevantcodes.extentreports.ExtentTest;
import amazon.application.base.BaseAction;
import amazon.application.base.Wrapper;
import io.appium.java_client.MobileDriver;


public class PDPPageAction extends Wrapper {
	private static Properties prop;
	public Map<String, String> capData1 = new HashMap<String, String>();

	public PDPPageAction(MobileDriver driver, ExtentTest test, Map<String, String> capData1) {
		this.driver = driver;
		this.test = test;
		this.capData1 = capData1;
		prop = new Properties();
		try {
			if (capData1.get("PlatformName").equalsIgnoreCase("Android")) {
				prop.load(new FileInputStream(new File("./Locators/Android/pdp.properties")));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Search a product and validate details
	public PDPPageAction validateSearchResultPage() throws InterruptedException {
		
		
		swipeFullFromBottomToTop("android");
		swipeFullFromBottomToTop("android");
		swipeFullFromBottomToTop("android");		
		
		BaseAction.productNameSearchResults=getText(prop.getProperty("text.prodSearchPageTitle"));
		String[] fullPrice=getText(prop.getProperty("text.prodSearchPagePrice")).split(" ");
		System.out.println("price full : "+fullPrice[0]);
		System.out.println("name full : "+BaseAction.productNameSearchResults);
		
		if(!fullPrice[0].contains("₹"))
		{
			org.testng.Assert.assertTrue(false,"₹ not present in search results page");
		}
		BaseAction.productPriceSearchResults=fullPrice[0];
		
		verifyStep("Product Name search page :"+BaseAction.productNameSearchResults, "INFO");
		verifyStep("Product Price search page :"+BaseAction.productPriceSearchResults, "INFO");

		click(prop.getProperty("text.prodSearchPageTitle"));
		return this;
	}
	
	// Validate PDP page
	public PDPPageAction validatePDPPage() {
		
			
		verifyElementIsDisplayed(prop.getProperty("text.productTitle"));
		verifyElementIsDisplayed(prop.getProperty("image.productReview"));
		verifyElementIsDisplayed(prop.getProperty("button.shareProduct"));
		verifyElementIsDisplayed(prop.getProperty("button.ProductImage"));
		
		verifyStep("Product details page displayed", "PASS");
	
		String pdpPageProductTitle=getText(prop.getProperty("text.productTitle"));
		verifyStep("Product name PDP page : "+pdpPageProductTitle, "INFO");

		org.testng.Assert.assertEquals(pdpPageProductTitle, BaseAction.productNameSearchResults);
		
		//swipeToElement("android", prop.getProperty("text.productPrice"));
		swipeFullFromBottomToTop("android");
		
		if(verifyIsDisplayed(prop.getProperty("text.pdpPageSavingsPrice")))
		{
			BaseAction.offerProduct=true;
		}
		
		String pdpPageProductPrice=getText(prop.getProperty("text.productPrice"));
		pdpPageProductPrice=pdpPageProductPrice.replace("rupees ", "₹");
		verifyStep("Product price PDP page : "+pdpPageProductPrice, "INFO");

		org.testng.Assert.assertEquals(pdpPageProductPrice, BaseAction.productPriceSearchResults);
			
		swipeToElement("android", prop.getProperty("button.wishListButton"));
		
		if(!verifyIsDisplayed(prop.getProperty("button.addtoCartButton")))
		{
			swipeToElementUpwards("android", prop.getProperty("button.oneTimePurchase"));
			click(prop.getProperty("button.oneTimePurchase"));
		}
		
		swipeToElement("android", prop.getProperty("button.addtoCartButton"));
		
		BaseAction.productQuantity=Integer.parseInt(getText(prop.getProperty("text.quantityDropdown")));
		
		verifyStep("Product quantity PDP page : "+BaseAction.productQuantity, "INFO");

		verifyElementIsDisplayed(prop.getProperty("button.addtoCartButton"));
	
	
			return this;
		}

	 // Adds the selected product to the cart and validates the cart count

	public PDPPageAction addToCart() throws InterruptedException {
		
		verifyElementIsDisplayed(prop.getProperty("text.cartCount"));
		int cartCountBefore=Integer.parseInt(getText(prop.getProperty("text.cartCount")));
		
		click(prop.getProperty("button.addtoCartButton"));
		
		
		
		verifyElementIsDisplayed(prop.getProperty("text.cartCount"));
		int cartCountAfter=Integer.parseInt(getText(prop.getProperty("text.cartCount")));
		
		
		if(cartCountBefore+BaseAction.productQuantity==cartCountAfter)
		{
			System.out.println("Product added to cart");
		}
	
		
		return this;
	}

	// Clicks on cart icon on top and navigates to shopping cart page
	public PDPPageAction naviogatetoShoppingCart() {
		
		verifyElementIsDisplayed(prop.getProperty("button.cartButton"));
		click(prop.getProperty("button.cartButton"));
		
		return this;
	}
		

}
