package pojo.Serialization;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import Files.payload;

public class SpecBuilderTest {
	public static void main(String[] args) {
	
	AddPlace p= new AddPlace();
	p.setAccuracy(50);
	p.setAddress("29, side layout, cohen 09");
	p.setLanguage("French-IN");
	p.setPhone_number("(+91) 983 893 3937");
	p.setWebsite("http://google.com");
	p.setName("Frontline house");
	
	List<String>myList=new ArrayList<String>();
	myList.add("shoe park");
	myList.add("shop");
	p.setTypes(myList);
	
	Location l=new Location();
	l.setLat(-38.383494);
	l.setLng(33.427362);
	p.setLocation(l);
	
	RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
	ResponseSpecification res= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
	/////below is the 1st way we have seen so far where request and response are in a single command
	String Resp=given().spec(req) 
	.body(p)
	.when().post("maps/api/place/add/json")
	.then().spec(res).extract().asString();
	
	////below is the another way where we can differentiate request and response , two commands -- good practice
	RequestSpecification request = given().spec(req).body(p);     //request
	Response response=res.when().post("maps/api/place/add/json").then().spec(res).extract().response();     //response 
	
	
	}
}
