package E2E_End_to_End;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		ResponseSpecification res=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		LoginRequest lreq= new LoginRequest();
		lreq.setUserEmail("abhishekale18@gmail.com");
		lreq.setUserPassword("Abhi@123");
		
		//Login
		RequestSpecification reqLogin=given().log().all().spec(req).body(lreq); //request
		//Response response=reqLogin.when().post("api/ecom/auth/login").then().log().all().spec(res).extract().response(); //response
		LoginResponse loginResponse=reqLogin.when().post("api/ecom/auth/login").then().log().all().spec(res).extract().response().as(LoginResponse.class);
		//LoginResponse lres = new LoginResponse();
		System.out.println(loginResponse.getToken());
		String token=loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId= loginResponse.getUserId();
		
		
		//Add product
		RequestSpecification addProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization",token).build(); //had to create this specBuilder again since content type is not JSON its form-data and we also have header this time

		RequestSpecification reqAddProduct= given().log().all().spec(addProductBaseReq).param("productName", "jacket")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Addias Originals")
		.param("productFor", "men")
		.multiPart("productImage", new File("C://Users//Abhishek Kale//Documents//World-Map-Black-Background-Wall-Art.jpg"));
		
		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		JsonPath js1=new JsonPath(addProductResponse);
		String productId=js1.get("productId");
		System.out.println(productId);
		
		//Create Order
		
		RequestSpecification reqCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token).setContentType(ContentType.JSON).build();
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails> ();
		orderDetailsList.add(orderDetails);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailsList);
		
		RequestSpecification createOrderRequest = given().log().all().spec(reqCreateOrder).body(orders);
		String createOrderResponse=createOrderRequest.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
		System.out.println(createOrderResponse);
		
		//Delete Product 
		
		RequestSpecification reqDeleteProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token).setContentType(ContentType.JSON).build();
		RequestSpecification deleteProductRequest=given().log().all().spec(reqDeleteProduct).pathParam("productId", productId);//Path Parameter
		
		String resDeleteProduct = deleteProductRequest.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString(); //Path Parameter
		
		JsonPath js2 = new JsonPath(resDeleteProduct);
		Assert.assertEquals("Product Deleted Successfully", js2.get("message"));
		
	}

	
}
