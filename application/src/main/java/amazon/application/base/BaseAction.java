package amazon.application.base;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.relevantcodes.extentreports.ExtentTest;
import amazon.application.utils.Reporter;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseAction extends Reporter {
	public MobileDriver driver;
	protected Properties prop;
	public Map<String, String> capability = new HashMap<String, String>();
	public static String productNameSearchResults = "";
	public static String productPriceSearchResults = "";
	public static int productQuantity;
	public static boolean offerProduct = false;
	public BaseAction(MobileDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}
	public BaseAction() {
	}
	public void startTestReport() {
		test = startTestCase(testCaseName, testDescription);
		test.assignCategory(category);
		test.assignAuthor(authors);
	}
	// Launch the application
	public void invokeApp() {
		URL urlObj = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		try {
			String dir = System.getProperty("user.dir");
			urlObj = new URL("http://" + "127.0.0.1" + ":" + capability.get("Port") + "/wd/hub");
			switch (capability.get("PlatformName").toLowerCase()) {
			case "android":
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capability.get("PlatformName"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capability.get("PlatformVersion"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capability.get("DeviceName"));
				capabilities.setCapability(MobileCapabilityType.UDID, capability.get("udid"));
				capabilities.setCapability("automationName", "UiAutomator2");
				capabilities.setCapability("systemPort", capability.get("systemPort"));
				capabilities.setCapability("newCommandTimeout", 9999);
				capabilities.setCapability(MobileCapabilityType.APP, dir + "/app/android/Amazon_shopping.apk");
				capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
				capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
				driver = new AndroidDriver<MobileElement>(urlObj, capabilities);
				System.out.println(" APP launched");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Clicks an element
	public void click(String property) {
		By byProperty = getLocator(property);
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(byProperty);
			driver.findElement(byProperty).click();
			System.out.println("Element is Clicked");

		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// Enter text
	public void enterText(String property, String data) {
		MobileElement element = null;
		MobileElement element2 = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			element.clear();
			element2 = (MobileElement) driver.findElement(getLocator(property));
			element2.sendKeys(data);
			System.out.println("Data is entered to the required Field");
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// Compares the given text with the element text in UI
	public boolean verifyText(String property, String text) {
		MobileElement element = null;
		String sText = "";
		boolean val = false;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			sText = element.getText();
			if (sText.equalsIgnoreCase(text)) {
				val = true;
			}
		} catch (Exception e) {
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertTrue(false, e.getMessage());
		}
		return val;
	}
	// Check whether the element is displayed
	public void verifyElementIsDisplayed(String property) {
		MobileElement element = null;
		try {
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			element = (MobileElement) driver.findElement(getLocator(property));
			element.isDisplayed();
		} catch (Exception e) {
			Assert.assertTrue(false, "Element not displayed\n" + e.getMessage());
		}
	}
	// Check whether the element is displayed or not (returns boolean value)
	public boolean verifyIsDisplayed(String property) {
		boolean present = false;
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			element.isDisplayed();
			present = true;

		} catch (Exception e) {
			present = false;
			e.printStackTrace();
		}
		return present;
	}
	// Verifies whether value is present or not
	public void verifyElementIsPresent(String property, long timeoutInSecs) {
		try {
			long startTime = System.currentTimeMillis();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while (System.currentTimeMillis() < (startTime + (timeoutInSecs * 1000))) {
				if (verifyIsDisplayed(property)) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					return;
				}
			}
			Assert.assertTrue(false, property + " is displayed or not validation");
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, property + "Element not found\n" + e.getMessage());
		}
		catch (TimeoutException e) {
			Assert.assertTrue(false, property + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, getLocator(property) + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, property + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, property + "Element not Interatable\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// Verfies element not present in the UI
	public void verifyElementIsNotPresent(String property, long timeoutInSecs) {
		try {
			long startTime = System.currentTimeMillis();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while (System.currentTimeMillis() < (startTime + (timeoutInSecs * 1000))) {
				if (verifyIsDisplayed(property)) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					return;
				}
			}
			Assert.assertTrue(true, property + " is displayed or not validation");
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(true, property + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, property + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, getLocator(property) + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, property + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, property + "Element not Interatable\n" + e.getMessage());
		}
		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// Get the text from the UI
	public String getText(String property) {
		String bReturn = "";
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			return element.getText();
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
		return bReturn;
	}
	// Gets the attribute value for the element
	public String getAttribute(String property, String attribute) {
		String bReturn = "";
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			return element.getAttribute(attribute);
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		}
		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
		return bReturn;
	}
	// Take screenshot
	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File("./reports/images/" + number + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("The app has been closed.");
		}
		return number;
	}
	// Get the locator from property file and extracts it based on
	// id,name,xpath, etc..
	public By getLocator(String property) {
		String locator = property;

		String locatorType = locator.split("===")[0];
		String locatorValue = locator.split("===")[1];

		if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return By.className(locatorValue);
		else if (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);

		else
			return null;
	}
	// check whether the checkbox, radio button is selected or not
	public boolean isSelected(String property) {
		boolean value = false;
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			value = driver.findElement(getLocator(property)).isSelected();

		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		}
		catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
		return value;
	}
	// Hide Keypad
	public void keypadDown() {
		driver.hideKeyboard();
	}
	// clears the text from the input field
	public void clearElement(String property) {
		MobileElement element = null;
		try {
			element = (MobileElement) driver.findElement(getLocator(property));
			element.clear();
		} catch (ElementNotFoundException e) {
			Assert.assertTrue(false, element + "Element not found\n" + e.getMessage());
		} catch (TimeoutException e) {
			Assert.assertTrue(false, element + "Time out error\n" + e.getMessage());
		} catch (ElementNotSelectableException e) {
			Assert.assertTrue(false, element + "Element not Selectable\n" + e.getMessage());
		} catch (ElementNotVisibleException e) {
			Assert.assertTrue(false, element + "Element not Visible\n" + e.getMessage());
		} catch (ElementNotInteractableException e) {
			Assert.assertTrue(false, element + "Element not Interatable\n" + e.getMessage());
		} catch (Exception e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// swipes on the screen from bottom to top that is swipe down
	public void swipeFullFromBottomToTop(String pfName) {
		System.out.println("Swiping......");
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int starty = (int) (scrnSize.height * 0.3);
			int endy = (int) (scrnSize.height * 0.8);
			if (pfName.equalsIgnoreCase("android")) {
				((AndroidDriver<WebElement>) driver).swipe(startx, endy, startx, starty, 1000);
			}

		} catch (InterruptedException e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// verifies whether element present or not
	public boolean verifyElement(String property) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		boolean present = true;
		try {
			driver.findElement(getLocator(property));
			return present;
		} catch (Exception e) {
			present = false;
			e.printStackTrace();
			return present;
		}
	}
	// Swipes to the element on the screen
	public void swipeToElement(String pfName, String property) {
		while (true) {
			if (verifyElement(property)) {
				break;
			}
			swipeFullFromBottomToTop(pfName);
		}
	}
	// swipes on the screen from top to bottom that is swipe down
	public void swipeFullFromTopToBottom(String pfName) {
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width / 2);
			int endy = (int) (scrnSize.height - 1);
			int starty = (int) (scrnSize.height * 0.2);
			if (pfName.equalsIgnoreCase("android")) {
				((AndroidDriver<WebElement>) driver).swipe(startx, starty, startx, endy, 3000);
			}
		} catch (InterruptedException e) {
			Assert.assertTrue(false, e.getMessage());
		}
	}
	// Swipes to the given elemet in upward direction
	public void swipeToElementUpwards(String pfName, String property) {
		while (true) {
			if (verifyElement(property)) {
				break;
			}
			swipeFullFromTopToBottom(pfName);
		}
	}
	// Launch the app on the device
	public void launchApp() {
		System.out.println("Launching the app");
		driver.launchApp();
	}
	// click on the android back button
	public void clickAndroidBack() {
		((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
	}
	// Click on WebElement
	public void clickElement(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Enter text
	public void enterText(WebElement element, String text) {
		try {
			element.clear();
			element.sendKeys(text);
			System.out.println("Text entered successfully" + text);
		} catch (Exception e) {
			System.out.println("Unable to enter Text");
			e.printStackTrace();
		}
	}
	// Get the text
	public String getText(WebElement element) {
		String Text = null;
		try {
			Text = element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Text;
	}
	// Get alert box text
	public String get_AlertText() {
		String alertText = null;
		try {
			alertText = driver.switchTo().alert().getText();
			System.out.println("alert Text is" + alertText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alertText;
	}
	// Dismiss alert
	public void DismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			System.out.println("Closed alert");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Close App
	public void closeApp() {
		try {
			driver.closeApp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}