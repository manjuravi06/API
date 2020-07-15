package jira.api.testing;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import resources.Payloads;

public class AddAttachment {

	@Test
	public void addAttachment() {

		RestAssured.baseURI = "http://localhost:8080";

		// User login to note the session details
		SessionFilter session = new SessionFilter();

		given().log().all().filter(session).header("Content-Type", "application/json").body(Payloads.jiraUserLogin())

				.when().post("/rest/auth/1/session")

				.then().log().all().assertThat().statusCode(200);

		// Add attachment to an issue

		given().log().all().filter(session).pathParam("issueId", "10014").header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-data").multiPart("file", new File("./JIRA_test.txt"))

				.when().post("/rest/api/2/issue/{issueId}/attachments")

				.then().assertThat().statusCode(200);

	}

}
