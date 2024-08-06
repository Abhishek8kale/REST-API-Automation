import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BasicsUpdate {
//Update Place with new Address API 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//given - all input details
		//when - Submit the API -resource,http method
		//Then - validate the response

		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("server","Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		System.out.println(response);
		
		//for parsing Json
		JsonPath js=new JsonPath(response);
		String placeID=js.getString("place_id");
		System.out.println(placeID);
		
		//update place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\"70 Summer walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().log().all().statusCode(200)
		.body("msg",equalTo("Address successfully updated"));
	
		//Get Place
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200);

	}


}
