package amazon.application.actions;

import amazon.application.base.BaseAction;
import amazon.application.base.Paths;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginApp extends BaseAction {

	public LoginApp(AndroidDriver<AndroidElement> driver) {
		super(driver, Paths.Login_App);
	}
	
	public void clickOnSkipBtn(){
		getElement("").click();
	}

}
