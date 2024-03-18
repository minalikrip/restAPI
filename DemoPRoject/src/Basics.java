import io.restassured.RestAssured;


import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;
public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RestAssured.baseURI="http://rahulshettyacademy.com";
		//Add Place through file placing in codespace
//		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
//		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
//					.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
//					.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
//		System.out.println(response);
		//Add Place through file placing in some particular folder path
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\mkriplan\\Documents\\AddPlace.json")))).when().post("maps/api/place/add/json")
							.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
							.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
				System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String place_id=js.getString("place_id");
		System.out.println(place_id);
		
		//Update Place
//		String newaddress="70 winter walk, USA";
//		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
//		.body("{\r\n"
//				+ "\"place_id\":\""+place_id+"\",\r\n"
//				+ "\"address\":\""+newaddress+"\",\r\n"
//				+ "\"key\":\"qaclick123\"\r\n"
//				+ "}\r\n"
//				+ "")
//		.when().put("maps/api/place/update/json")
//		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
//		
//		//Get Place
//		String getPlaceresponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
//		.when().get("maps/api/place/get/json")
//		.then().assertThat().log().all().statusCode(200).extract().response().asString();
//		
//		JsonPath js1=new JsonPath(getPlaceresponse);
//				String equal_addr=js1.getString("address");
//				Assert.assertEquals(newaddress, equal_addr);
//				
	}

}
