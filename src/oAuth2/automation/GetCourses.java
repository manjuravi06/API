package oAuth2.automation;

import resources.ReusableMethods;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import pojo.classes.oAuth.Api;
import pojo.classes.oAuth.GetCoursesPojo;
import pojo.classes.oAuth.WebAutomation;

public class GetCourses {

	public static void main(String[] args) {

		// Get auth code
		String authCode = "https://rahulshettyacademy.com/getCourse.php?code=4%2F1gEz_vSPNy_nJPWHPqiEZNGirt_WvEPo2KuKbkFWjIe3aDiOA58pCiB2dMlSU-QwadrGBkFe9_5B96sDmkVdFVA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&hd=perfios.com&prompt=none#";

		String code = authCode.split("code=")[1].split("&scope")[0];

		System.out.println(code);

		// Get access token
		String accessTokenResponse = given().log().all().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code")

				.when().post("https://www.googleapis.com/oauth2/v4/token").asString();

		String accessToken = ReusableMethods.rawToJson(accessTokenResponse).get("access_token").toString();

		System.out.println(accessToken);

		GetCoursesPojo gc = given().log().all().queryParam("access_token", accessToken).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCoursesPojo.class);

		System.out.println(gc.getInstructor());

		List<Api> apiList = gc.getCourses().getApi();

		for (int i = 0; i < apiList.size(); i++) {

			if (apiList.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {

				System.out.println(apiList.get(i).getPrice());

			}

		}

		String[] expected = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		ArrayList<String> actual = new ArrayList<>();

		List<WebAutomation> wb = gc.getCourses().getWebAutomation();

		for (int i = 0; i < wb.size(); i++) {

			actual.add(wb.get(i).getCourseTitle());

		}

		List<String> e = Arrays.asList(expected);

		Assert.assertEquals(actual, e);

	}

}
