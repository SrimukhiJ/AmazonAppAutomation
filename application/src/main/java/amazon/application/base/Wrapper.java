package amazon.application.base;
import java.io.IOException;
import org.testng.ITestListener;
import org.testng.ITestResult ;	
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class Wrapper extends BaseAction implements ITestListener {
	public String dataSheetName;
	public String applicationType;
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		startResult();	
	}
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		invokeApp();
	}
	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		if(!result.isSuccess())
		{
		    String testResult = String.valueOf(result.getThrowable());
			verifyStep(testResult, "FAIL");
		}
		endTestcase();
	}
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		endResult();
		closeApp();
	}
}
