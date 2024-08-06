import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.payload;
import io.restassured.RestAssured;
//import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="xyz")
	public void addBook(String isbn, String aisle)
	
	{
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		String resp= given().log().all().header("Content-Type","application/json").
				body(payload.Addbook(isbn,aisle)).when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		 JsonPath js= new JsonPath(resp); 
		 String id=js.getString("ID");
		 System.out.println(id);
		 
	}
	
	@DataProvider(name="xyz")
	public Object[][] getData()
	{
		//array -> collection of elements 
		//multidimensional array -> collection of arrays
		return new Object[][] {{"sffda","5434"},{"weoss","4313"},{"ednao","3323"}};
	}
}
