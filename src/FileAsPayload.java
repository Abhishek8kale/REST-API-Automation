import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.Assert;
import Files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class FileAsPayload {
 		//content of the file to String ==>> content of file is converted into Bytes -> then bytes data is converted to String

		public static void main(String[] args) throws IOException{

			RestAssured.baseURI="https://rahulshettyacademy.com";
			String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Abhishek Kale\\Documents\\addPlace.json")))).when().post("maps/api/place/add/json")
			.then().assertThat().statusCode(200)
			.body("scope", equalTo("APP"))
			.header("server","Apache/2.4.52 (Ubuntu)")
			.extract().response().asString();
			System.out.println(response);
			
			JsonPath js=new JsonPath(response);
			String placeID=js.getString("place_id");
			System.out.println(placeID);
			
			//update place
			String newAddress = "Summer Walk Africa";
			
			given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+placeID+"\",\r\n"
					+ "\"address\":\""+newAddress+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}").when().put("maps/api/place/update/json")
			.then().log().all().statusCode(200)
			.body("msg",equalTo("Address successfully updated"));
		
			//Get Place
			String getPlaceResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
			.when().get("maps/api/place/get/json")
			.then().assertThat().log().all().statusCode(200).extract().response().asString();

			JsonPath js2= new JsonPath(getPlaceResponse);
			String actualAddress=js2.getString("address");
			System.out.println(actualAddress);
			Assert.assertEquals(actualAddress, newAddress);
		}


	}

