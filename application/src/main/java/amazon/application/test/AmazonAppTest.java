package amazon.application.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import amazon.application.actions.CheckoutPageAction;
import amazon.application.actions.HomePageAction;
import amazon.application.actions.LoginPageAction;
import amazon.application.actions.PDPPageAction;
import amazon.application.base.Wrapper;

public class AmazonAppTest extends Wrapper {
	@BeforeClass
	@Parameters({ "Automation", "PlatformName", "PlatformVersion", "DeviceName", "udid", "Application", "Port", "systemPort" })
	public void setData(String automation, String platformNam, String platformVer, String deviceNam, String udid,
			String application, String port, String systemPort) {
		testCaseName = "Test";
		tcName = testCaseName;
		testDescription = "Started";
		category = "Functional";
		dataSheetName = "DataPool.xlsx";
		applicationType = application;
		authors = "Sundar";
		capability.put("Automation", automation);
		capability.put("Port", port);
		capability.put("PlatformName", platformNam);
		capability.put("PlatformVersion", platformVer);
		capability.put("DeviceName", deviceNam);
		capability.put("udid", udid);
		capability.put("TestCaseName", testCaseName);
		capability.put("systemPort", systemPort);
	}
	@Test(priority = 1, enabled = true)
	public void Amazon_Login_And_Place_Order_TC001() {
		try {
			testCaseName = "Amazon_Login_And_Place_Order_TC001";
			tcName = testCaseName;
			testDescription = "Validate the login and place order scenario in Amazon Android mobile app";
			startTestReport();
			LoginPageAction login = new LoginPageAction(driver, test, capability);
			login.signIn();
			HomePageAction home=new HomePageAction(driver, test, capability);
			home.selectLanguage();
			home.validateLogin();
			home.selectLanguage();
			home.clearCart();
			home.searchProduct();
			PDPPageAction pdp=new PDPPageAction(driver, test, capability);
			pdp.validateSearchResultPage();
			home.selectLanguage();
			pdp.validatePDPPage();
			pdp.addToCart();
			pdp.naviogatetoShoppingCart();
			CheckoutPageAction checkout=new CheckoutPageAction(driver, test, capability);
			checkout.validateShoppingCart();
			checkout.proceedtoBuy();
			checkout.selectShippingAddress();
			checkout.selectPayment();
			checkout.validateCheckoutPage();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}

	



