package amazon.application.test;

import org.testng.annotations.Test;
import amazon.application.actions.LoginApp;
import amazon.application.base.BaseTest;

public class LoginAppTest extends BaseTest {
	
	@Test
	public void clickOnSkipBtn(){
	 LoginApp login=new LoginApp(driver);
	 login.clickOnSkipBtn();
	}
}
	



