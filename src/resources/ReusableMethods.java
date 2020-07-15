package resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

	public static JsonPath rawToJson(String response) {

		return new JsonPath(response);
	}

	public static String staticJson(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
