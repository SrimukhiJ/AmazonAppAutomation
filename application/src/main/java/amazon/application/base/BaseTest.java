package amazon.application.base;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

//import bsh.Capabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class BaseTest {
    public Properties config;
    public AndroidDriver<AndroidElement> driver;
    public WebDriver webdriver;

    @BeforeTest
    public void setConfig(){
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

    @BeforeMethod
    @Parameters({"gridURL","device"})
    public void initialize(String gridURL,String device ) throws Exception {
        DesiredCapabilities cap=new DesiredCapabilities();
        File f=new File("src/main/resources/4.0.9-Dev.apk");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME,device );
        cap.setCapability(MobileCapabilityType.APP,f.getAbsolutePath() );      
        cap.setCapability("appPackage","com.moveinsync.driver.byod");	
        cap.setCapability("appActivity","com.moveinsync.driver.byod.activities.SplashActivity");  
        driver=new AndroidDriver<AndroidElement>(new URL(gridURL),cap);      
	}
}
    
/*    public void configureApp() {
		WebDriverWait wait = new WebDriverWait(driver, 5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.moveinsync.driver.byod:id/buid_tv")));
	}*/

	/*@AfterMethod
    public void closeDriver(){
AS																																											Aaz3333333
        driver.closeApp();
    }*/
