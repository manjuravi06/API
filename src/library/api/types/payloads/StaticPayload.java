package library.api.types.payloads;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.ReusableMethods;

public class StaticPayload {

	@Test
	public void dynamicPayload() throws IOException {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://216.10.245.166")
				.setContentType(ContentType.JSON).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		RequestSpecification request = given().log().all().spec(req)
				.body(ReusableMethods.staticJson("./JsonFiles/StaticJson.json"));

		String response = request.when().post("/Library/Addbook.php").then().log().all().spec(res).extract().response()
				.asString();

		String bookId = ReusableMethods.rawToJson(response).getString("ID");

		System.out.println(bookId);

	}

}
