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
		click(prop.getProperty("radio.languageEnglishSettings"));
		click(prop.getProperty("button.saveChangesButton"));
		return this;
	}	
	//Validate Login
	public HomePageAction validateLogin() {		
		ExcelFetch excelFetch = new ExcelFetch();
		Map<String, String> signinDetailsMap = excelFetch.getDataFromExcel("Login_And_Place_Order_TC01", "account1");
		click(prop.getProperty("button.menuButton"));
		verifyStep("Logged in successfully", "PASS");
		String actualName=getText(prop.getProperty("text.helloText"));
		actualName=actualName.replace("Hello, ", "");
        String expectedName=signinDetailsMap.get("Name");        
        Assert.assertEquals(actualName, expectedName);        
		click(prop.getProperty("button.homeButton"));		
		return this;
	}	
	// Search product
	public HomePageAction searchProduct() throws InterruptedException {
		ExcelFetch excelFetch = new ExcelFetch();
		Map<String, String> searchData = excelFetch.getDataFromExcel("Login_And_Place_Order_TC01", "account1");		
		click(prop.getProperty("edit.searchBar"));
		enterText(prop.getProperty("edit.searchBar"), searchData.get("SearchTerm"));	
		verifyStep("Product searched", "PASS");
		click(prop.getProperty("text.searchResult1"));		
		return this;
	}
}
