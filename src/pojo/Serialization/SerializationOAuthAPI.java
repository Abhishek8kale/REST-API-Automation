package pojo.Serialization;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import Files.ReUsableMethods;
import Files.payload;

public class SerializationOAuthAPI {
	public static void main(String[] args) {
	RestAssured.baseURI="https://rahulshettyacademy.com";
	
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
	
	//serialization
	String Resp=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
	.body(p)
	.when().post("maps/api/place/add/json")
	.then().log().all().assertThat().statusCode(200).extract().asString();
	
	
	
	//Deserialization -- just as example (not real)
	JsonPath js=ReUsableMethods.rawToJson(Resp);
	String placeID=js.getString("place_id");
	System.out.println(placeID);
	
	AddPlace Resp2=given().queryParam("key", "qaclick123").queryParam("place_id", placeID).header("Content-Type", "application/json")
			.when().get("maps/api/place/get/json")
			.then().extract().as(AddPlace.class);
	System.out.println(Resp2.getPhone_number());  //this is just an exmaple, it wont work since this (maps/api/place/get/json) get api is not designed as such where phonenumber field is present 
	
	}
}
