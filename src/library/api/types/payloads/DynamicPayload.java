package library.api.types.payloads;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Payloads;
import resources.ReusableMethods;

import static io.restassured.RestAssured.*;

public class DynamicPayload {

	@Test(dataProvider = "bodyData")
	public void dynamicPayload(String isbn, String aisle) {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://216.10.245.166")
				.setContentType(ContentType.JSON).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		RequestSpecification request = given().log().all().spec(req).body(Payloads.addBook(isbn, aisle));

		String response = request.when().post("/Library/Addbook.php").then().log().all().spec(res).extract().response()
				.asString();

		String bookId = ReusableMethods.rawToJson(response).getString("ID");

		System.out.println(bookId);

		Assert.assertEquals(bookId, isbn + aisle);

	}

	@DataProvider(name = "bodyData")
	public Object[][] bodyData() {

		return new Object[][] { { "RedBull01", "05" }, { "RedBull01", "06" }, { "RedBull01", "07" } };

	}

}
