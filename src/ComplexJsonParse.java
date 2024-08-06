import Files.payload;
/*import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import io.restassured.RestAssured;*/
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js=new JsonPath(payload.CoursePrice());
		
		// Print No of courses returned by API
		int count=js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Title of the first course
		String titleFirstCourse=js.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print Purchase Amount
		int totalAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print All course titles and their respective Prices
		for (int i=0 ; i<count ; i++) {
			String title=js.get("courses["+i+"].title");
			System.out.println(title);
			System.out.println(js.get("courses["+i+"].price").toString()); // club 29 & 30 together to convert it directly to string and print simultaneously 
		}
		
		//Print no of copies sold by RPA Course
		System.out.println("num of copies sold by RPA Course");
		for (int i=0 ; i<count ; i++) {
			String title=js.get("courses["+i+"].title");
			if (title.equalsIgnoreCase("RPA"))
			{
				int copies=js.get("courses["+i+"].copies");
				System.out.println(copies);
				break; //once we found RPA we dont want to iterate any more so we use break statement 

			}
		}
	
	}
}
