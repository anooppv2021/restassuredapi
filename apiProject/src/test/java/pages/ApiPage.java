package pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.ApiTestBase;

public class ApiPage extends ApiTestBase {

	public ApiPage(WebDriver driver) {

		RestAssured.baseURI = "https://reqres.in";

	}

	public void GetBookHeaders() {

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/api/users?page=2");
		String responseBody = response.getBody().asPrettyString();
		System.out.println("Response Body is: " + responseBody);

		JsonPath jsonPathEvaluator = response.jsonPath();
		String sEmail = jsonPathEvaluator.get("data[0].email");
		System.out.println(sEmail);

		int res = response.getStatusCode();

		if ((sEmail.contains("michael")) && (res == 200)) {

			logger.log(Status.INFO, "API test is passed: Response Body is: " + responseBody);
			Assert.assertTrue(true);

		} else {

			logger.log(Status.INFO, "API test is Failed: Response Body is: " + responseBody);
			Assert.assertTrue(false);
		}

	}

}