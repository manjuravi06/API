package resources;

public class Payloads {

	public static String addBook(String isbn, String aisle) {

		return "{\r\n" + "\"name\":\"The invisibility cloak\",\r\n" + "\"isbn\":\"" + isbn + "\",\r\n" + "\"aisle\":\""
				+ aisle + "\",\r\n" + "\"author\":\"Harry Potter 7\"\r\n" + "}";
	}

	public static String jiraUserLogin() {

		return "{\"username\":\"manjuravi06\",\"password\":\"falcon123\"}";
	}

	public static String jiraAddIssue() {

		return "{\r\n" + "    \"fields\": {\r\n" + "        \"project\": {\r\n" + "            \"key\": \"TEST\"\r\n"
				+ "        },\r\n" + "        \"summary\": \"First Issue\",\r\n"
				+ "        \"description\": \"description\",\r\n" + "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n" + "        }\r\n" + "    }\r\n" + "}";
	}

	public static String jiraAddComment() {

		return "{\r\n" + "    \"body\": \"This is my final comment\",\r\n" + "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}";
	}
	
	
}
