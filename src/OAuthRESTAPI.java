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

public class OAuthRESTAPI {

	public static void main(String[] args) {
		
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope","trust").when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all().extract().asString();
		System.out.println(Response);
		
		JsonPath js=new JsonPath(Response);
		String acsTkn=js.getString("access_token");
		
		String Response2= given().queryParam("access_token",acsTkn).when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").then().log().all().extract().asString();
		System.out.println(Response2);
	}

}
