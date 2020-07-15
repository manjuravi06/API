package jira.api.testing;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Payloads;
import resources.ReusableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateIssue {

	public static String issueId = "test";
	
	
	@Test
	public void addIssue() {

		// Create session and pass the session details to future actions

		SessionFilter session = new SessionFilter();

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://localhost:8080")
				.setContentType(ContentType.JSON).addFilter(session).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();

		RequestSpecification userLoginRequest = given().log().all().spec(req).body(Payloads.jiraUserLogin());

		String response = userLoginRequest.when().post("/rest/auth/1/session").then().log().all().spec(res)
				.body("session.name", equalTo("JSESSIONID")).extract().response().asString();

		System.out.println(ReusableMethods.rawToJson(response).getString("session.value"));

		RequestSpecification addIssue = given().log().all().spec(req).body(Payloads.jiraAddIssue());

		String addIssueResponse = addIssue.when().post("/rest/api/2/issue").then().log().all().spec(res).extract()
				.response().asString();

		CreateIssue.issueId = ReusableMethods.rawToJson(addIssueResponse).getString("id");

		System.out.println(issueId);
	}

}
