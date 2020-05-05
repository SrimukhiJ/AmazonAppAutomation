package amazon.application.base;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import amazon.application.base.*;

import java.io.*;
import java.util.*;



public class BaseAction {
	protected AndroidDriver<AndroidElement> driver;
	String fileName;
	public Properties prop;
	public Properties config;
	public WebDriverWait wait;
	public FileOutputStream fos;
	protected int rowSize;
	protected WebDriver webdriver;

	public BaseAction(AndroidDriver<AndroidElement> driver, String fileName) {
		wait = new WebDriverWait(driver, 240);
		this.driver = driver;
		this.fileName = fileName;

		prop = new Properties();
		try {
			FileInputStream fis= new FileInputStream(Paths.PAGES + fileName);
			
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = new Properties();
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/Config.properties");
			config.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BaseAction(WebDriver webdriver, String loginets) {
		// TODO Auto-generated constructor stub
		
		wait = new WebDriverWait(webdriver, 240);
		this.webdriver = driver;
		this.fileName = fileName;

		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(Paths.PAGES + fileName);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		config = new Properties();
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/Config.properties");
			config.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WebElement getElement(String ref) {
		try {
			FileInputStream fis = new FileInputStream(Paths.PAGES + fileName);
			prop.load(fis);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		WebElement element1 = null;
		String record = prop.getProperty(ref);
		String way = record.split("=")[0];
		String key = record.substring(way.length() + 1);
		WebDriverWait wt = new WebDriverWait(driver, 30);
		if (way.equals("id")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.id(key)));
		} else if (way.equals("name")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.name(key)));
		} else if (way.equals("xpath")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(key)));
		} else if (way.equals("linktext")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(key)));
		} else if (way.equals("cssselector")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(key)));
			element1 = wt.until(ExpectedConditions.elementToBeClickable(element1));
		} else if (way.equals("class")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.className(key)));
		} else if (way.equals("tagName")) {
			element1 = wt.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(key)));
		}
		return element1;
	}


	public WebElement getElement(WebElement ele, String ref) {
		WebElement element1 = null;
		String record = prop.getProperty(ref+"");
		String way = record.split("=")[0];
		String key = record.substring(way.length() + 1);

		if (way.equals("id")) {
			element1 = ele.findElement(By.id(key));
		} else if (way.equals("name")) {
			element1 = ele.findElement(By.name(key));
		} else if (way.equals("xpath")) {
			element1 = ele.findElement(By.xpath(key));
		} else if (way.equals("linktext")) {
			element1 = ele.findElement(By.linkText(key));
		} else if (way.equals("cssselector")) {
			element1 = ele.findElement(By.cssSelector(key));
		} else if (way.equals("class")) {
			element1 = ele.findElement(By.className(key));
		} else if (way.equals("tagName")) {
			element1 = ele.findElement(By.tagName(key));
		}

		return element1;
	}

	public List<MobileElement> getElements(AndroidElement ele, String ref) {
		try {
			FileInputStream fis = new FileInputStream(Paths.PAGES + fileName);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<MobileElement> elements = null;
		String record = prop.getProperty(ref);
		String way = record.split("=")[0];
		String key = record.substring(way.length() + 1);

		if (way.equals("id")) {
			elements = ele.findElements(By.id(key));
		} else if (way.equals("name")) {
			elements = ele.findElements(By.name(key));
		} else if (way.equals("xpath")) {
			elements = ele.findElements(By.xpath(key));
		} else if (way.equals("linktext")) {
			elements = ele.findElements(By.linkText(key));
		} else if (way.equals("cssselector")) {
			elements = ele.findElements(By.cssSelector(key));
		} else if (way.equals("class")) {
			elements = ele.findElements(By.className(key));
		} else if (way.equals("tagName")) {
			elements = ele.findElements(By.tagName(key));
		}
		return elements;
	}

	public List<AndroidElement> getElements(String ref) {
		List<AndroidElement> elements = null;
		String record = prop.getProperty(ref);
		String way = record.split("=")[0];
		String key = record.substring(way.length() + 1);

		if (way.equals("id")) {
			elements = driver.findElements(By.id(key));
		} else if (way.equals("name")) {
			elements = driver.findElements(By.name(key));
		} else if (way.equals("xpath")) {
			elements = driver.findElements(By.xpath(key));
		} else if (way.equals("linktext")) {
			elements = driver.findElements(By.linkText(key));
		} else if (way.equals("cssselector")) {
			elements = driver.findElements(By.cssSelector(key));
		} else if (way.equals("class")) {
			elements = driver.findElements(By.className(key));
		} else if (way.equals("tagName")) {
			elements = driver.findElements(By.tagName(key));
		}
		return elements;
	}


    public WebElement getWebElementFor(String ref) {
		try {
			FileInputStream fis = new FileInputStream(Paths.PAGES + fileName);
			prop.load(fis);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		WebElement element = null;
		String arr[];
		if (ref.contains(("::"))) {
			arr = ref.split("::");
			for (int i = 0; i < arr.length; i++) {
				if (element == null) {
					element = getElement(arr[i]);
				} else {
					element = getElement(element, arr[i]);
				}
			}
		} else {
			element = getElement(ref);
		}
		return element;
	}

	public void explicitWait() throws InterruptedException {
		Thread.sleep(1000);
		WebDriverWait wait11 = new WebDriverWait(driver, 180);
		wait11.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("div.imgClass"), "Loading..."));
	}
	
}