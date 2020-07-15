package jira.api.testing;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import resources.Payloads;
import resources.ReusableMethods;

import static io.restassured.RestAssured.*;

public class GetComment {

	@Test
	public void getComment() {

		RestAssured.baseURI = "http://localhost:8080";

		// User login to note the session details
		SessionFilter session = new SessionFilter();

		given().log().all().filter(session).header("Content-Type", "application/json").body(Payloads.jiraUserLogin())

				.when().post("/rest/auth/1/session")

				.then().log().all().assertThat().statusCode(200);

		// Adding a comment to already existing issue
		String addCommentResponse = given().log().all().filter(session).pathParam("issueId", "10014")
				.header("Content-Type", "application/json").body(Payloads.jiraAddComment()).when()
				.post("/rest/api/2/issue/{issueId}/comment").then().assertThat().statusCode(201).extract().response()
				.asString();

		String commentId = ReusableMethods.rawToJson(addCommentResponse).get("id").toString();
		String commentBody = ReusableMethods.rawToJson(addCommentResponse).get("body").toString();

		// Get the details of the comment
		String getCommentResponse = given().log().all().filter(session).queryParam("fields", "comment")
				.pathParam("issueId", "10014")

				.when().get("rest/api/2/issue/{issueId}")

				.then().log().all().extract().response().asString();

		int commentArraySize = ReusableMethods.rawToJson(getCommentResponse).getInt("fields.comment.comments.size()");

		System.out.println(commentArraySize);

		for (int i = 0; i < commentArraySize; i++) {

			if (ReusableMethods.rawToJson(getCommentResponse).get("fields.comment.comments[" + i + "].id").toString()
					.equalsIgnoreCase(commentId)) {

				String actualCommentBody = ReusableMethods.rawToJson(getCommentResponse)
						.get("fields.comment.comments[" + i + "].body").toString();

				System.out.println(actualCommentBody);

				Assert.assertEquals(actualCommentBody, commentBody);
			}

		}

	}

}
