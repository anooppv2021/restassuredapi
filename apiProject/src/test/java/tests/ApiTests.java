package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import pages.ApiPage;

public class ApiTests extends ApiTestBase {

	@Test(priority = 1, enabled = true, testName = "To verify API request", description = "API")
	public void apiFirstTest() throws Exception {

		ApiPage homepage = PageFactory.initElements(driver, ApiPage.class);
		homepage.GetBookHeaders();

	}

}