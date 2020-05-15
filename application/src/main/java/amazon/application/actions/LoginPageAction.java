package amazon.application.actions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.relevantcodes.extentreports.ExtentTest;
import amazon.application.base.Wrapper;
import io.appium.java_client.MobileDriver;
import amazon.application.utils.*;

public class LoginPageAction extends Wrapper {
	private static Properties prop;
	public Map<String, String> capabality = new HashMap<String, String>();
	//Constructor
	public LoginPageAction(MobileDriver driver, ExtentTest test, Map<String, String> capabality) {
		this.driver = driver;
		this.test = test;
		this.capabality = capabality;
		prop = new Properties();
		try {
			if (capabality.get("PlatformName").equalsIgnoreCase("Android")) {
				prop.load(new FileInputStream(new File("./Locators/Android/logIn.properties")));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Gets the input from the excel sheet and login to the application
	public LoginPageAction signIn() throws InterruptedException {
		ExcelFetch excelFetch = new ExcelFetch();
		Map<String, String> signinDetailsMap = excelFetch.getDataFromExcel("Login_And_Place_Order_TC01", "account1");
		System.out.println("map" + signinDetailsMap);
		verifyStep("App launched", "PASS");
		verifyElementIsDisplayed(prop.getProperty("button.signInButtonNavigation"));
		click(prop.getProperty("button.signInButtonNavigation"));
		enterText(prop.getProperty("edit.emailField"), signinDetailsMap.get("Username"));
		click(prop.getProperty("button.continueButton"));
		verifyStep("Login Page displayed", "PASS");
		enterText(prop.getProperty("edit.passwordField"), signinDetailsMap.get("Password"));
		click(prop.getProperty("button.logInButton"));
		return this;
	}
}
