package serialization.add.place;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AddPlace {

	@Test
	public void addPlace() {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		SerailizationPojo sp = new SerailizationPojo();

		sp.setAccuracy(100);
		sp.setName("Manjunath");
		sp.setPhone_number("1213215161");
		sp.setAddress("Bangalore, Karnatak, India");
		sp.setWebsite("www.google.com");
		sp.setLanguage("Kannada");

		List<String> type = new ArrayList<String>();

		type.add("Hello");
		type.add("World");

		sp.setTypes(type);

		Location l = new Location();
		l.setLat(25.2525);
		l.setLng(-14.1414);

		sp.setLoaction(l);

		RequestSpecification addPlaceRequest = given().log().all().spec(req).body(sp);

		String addPlaceResponse = addPlaceRequest.when().post("/maps/api/place/add/json")

				.then().log().all().spec(res).extract().response().asString();

		System.out.println(addPlaceResponse);

	}

}
