import Files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.filter.session.SessionFilter;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.Assert;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JiraAPITest {

	public static void main(String[] args) {
		
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		String response=given().log().all().header("Content-Type","application/json")
		.body(payload.loginToJira()).log().all().filter(session).when().post("/rest/auth/1/session")
		.then().extract().response().asString();
		
		given().log().all().pathParam("key","10100").header("Content-Type","application/json")
				.body(payload.addComment()).filter(session).when().post("rest/api/2/issue/{key}/comment")
				.then().assertThat().log().all().statusCode(201);
		
		//add attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key","10100")
		.header("Content-Type","multipart/form-data").multiPart("file",new File ("jira.txt")).when().
		post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		//GET Issue -- using both path parameter and query parameter
		
		String issueDetails=given().log().all().pathParam("key","10100").queryParam("fields","comment")
				.log().all().when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		

	}

}
