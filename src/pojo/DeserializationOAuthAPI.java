package pojo;
import Files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.filter.session.SessionFilter;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DeserializationOAuthAPI {

	public static void main(String[] args) {
		
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope","trust").when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all().extract().asString();
		System.out.println(Response);
		
		JsonPath js=new JsonPath(Response);
		String acsTkn=js.getString("access_token");
		
		GetCourse gc= given()
				.queryParam("access_token",acsTkn)
				.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.then().extract().as(GetCourse.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getUrl());
		
		System.out.println(gc.getCourses().getWebAutomation().get(1).getCourseTitle());
		
		//Print the Course Price for SoapUI Webservices testing
		List<Api> apiCourses=gc.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Print all course titles under Web automation course
		
		for (int i=0; i<gc.getCourses().getWebAutomation().size();i++) {
			System.out.println(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
		}
		
		
		//compare response course title with actual course title
		
		ArrayList<String> a= new ArrayList<String>(); //we use ArrayList instead of array because sometimes we are not sure about the array size as it may be dynamic(changing size)
		String [] courseTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
		List<String> expectedList= Arrays.asList(courseTitles);
		
		for (int i=0; i<gc.getCourses().getWebAutomation().size();i++) {
			a.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle()); //adds list of titles in arraylist object
		}
		
		Assert.assertTrue(a.equals(expectedList));
		
	}

}
