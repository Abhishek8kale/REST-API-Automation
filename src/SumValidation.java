
import org.testng.Assert;
import org.testng.annotations.Test;

import Files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	//Verify if Sum of all Course prices matches with Purchase Amount
	//instead of public static class we are using testng framework @test annotation to run our method without public static ...
			
	@Test
	public void sumOfCourses()
	//public static void main(String[] args)
	{
		JsonPath js= new JsonPath(payload.CoursePrice());
		
		int totalSum=0;
		
		int count=js.getInt("courses.size()");
		
		
		for (int i=0 ; i<count ; i++)
		{
			int coursePrice=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int amountPerCourse= coursePrice*copies;
			System.out.println(amountPerCourse);
			totalSum=totalSum+amountPerCourse;
		}
		System.out.println(totalSum);
		
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, totalSum);
	}

}
