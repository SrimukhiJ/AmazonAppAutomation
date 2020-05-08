package amazon.application.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentTest;
import amazon.application.base.Wrapper;
import io.appium.java_client.MobileDriver;
import amazon.application.utils.*;

public class HomePageAction extends Wrapper {
	private static Properties prop;
	public Map<String, String> capability = new HashMap<String, String>();

	public HomePageAction(MobileDriver driver, ExtentTest test, Map<String, String> capability) {
		this.driver = driver;
		this.test = test;
		this.capability = capability;
		prop = new Properties();
		try {
			if (capability.get("PlatformName").equalsIgnoreCase("Android")) {
				prop.load(new FileInputStream(new File("./Locators/Android/homePage.properties")));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// Selects the language
	public HomePageAction selectLanguage() {
		verifyElementIsDisplayed(prop.getProperty("text.languageSelectionPopUp"));
		verifyElementIsDisplayed(prop.getProperty("radio.languageEnglishSettings"));
		click(prop.getProperty("radio.languageEnglishSettings"));
		verifyElementIsDisplayed(prop.getProperty("button.saveChangesButton"));
		click(prop.getProperty("button.saveChangesButton"));
		return this;
	}
	
	//Validate Login
	public HomePageAction validateLogin() {
		
		ExcelFetch excelFetch = new ExcelFetch();
		Map<String, String> signinDetailsMap = excelFetch.getDataFromExcel("Login_And_Place_Order_TC01", "account1");

		verifyElementIsDisplayed(prop.getProperty("button.menuButton"));
		click(prop.getProperty("button.menuButton"));
		
		verifyElementIsDisplayed(prop.getProperty("text.helloText"));
		
		verifyStep("Logged in successfully", "PASS");

		String actualName=getText(prop.getProperty("text.helloText"));
		actualName=actualName.replace("Hello, ", "");
        String expectedName=signinDetailsMap.get("Name");
        
        Assert.assertEquals(actualName, expectedName);
        
    	verifyElementIsDisplayed(prop.getProperty("button.homeButton"));
		click(prop.getProperty("button.homeButton"));
		
		return this;
	}
	
	 // Validates the elements in the homepage
	 	public HomePageAction validateHomePage() {
		verifyElementIsDisplayed(prop.getProperty("image.amazonHomeLogo"));
		verifyElementIsDisplayed(prop.getProperty("button.microphoneButton"));
   	    verifyElementIsDisplayed(prop.getProperty("button.locationSelection"));   
		verifyStep("Home Page Validated", "PASS");

		return this;
	}

	// Search product
	public HomePageAction searchProduct() throws InterruptedException {

		ExcelFetch excelFetch = new ExcelFetch();
		Map<String, String> searchData = excelFetch.getDataFromExcel("Login_And_Place_Order_TC01", "account1");
		
		verifyElementIsDisplayed(prop.getProperty("edit.searchBar"));
		click(prop.getProperty("edit.searchBar"));
		
		
		verifyElementIsDisplayed(prop.getProperty("edit.searchBar"));
		enterText(prop.getProperty("edit.searchBar"), searchData.get("SearchTerm"));
		
		verifyStep("Product searched", "PASS");

		
		verifyElementIsDisplayed(prop.getProperty("text.searchResult1"));
		click(prop.getProperty("text.searchResult1"));
		
		
		verifyElementIsDisplayed(prop.getProperty("image.searchListPageImage1"));
		
		
		return this;
	}
	
	//Clear cart items
	public HomePageAction clearCart() throws InterruptedException {
		
		verifyElementIsDisplayed(prop.getProperty("text.cartCount"));
		int cartCount=Integer.parseInt(getText(prop.getProperty("text.cartCount")));
		
		if(cartCount>0)
		{
			System.out.println("Deleting cart items");
			verifyElementIsDisplayed(prop.getProperty("button.cartButton"));
			click(prop.getProperty("button.cartButton"));
			
			verifyElementIsDisplayed(prop.getProperty("button.deleteButton"));
			click(prop.getProperty("button.deleteButton"));
						
			int cartCountUpdated=Integer.parseInt(getText(prop.getProperty("text.cartCount")));

			if(cartCountUpdated==0)
			{
				System.out.println("cart items deleted successfully");
				
				verifyStep("Cart items deleted", "PASS");

			}		
		}
		verifyElementIsDisplayed(prop.getProperty("image.amazonHomeLogo"));
		click(prop.getProperty("image.amazonHomeLogo"));
		
		return this;
	}
}
