import static io.restassured.RestAssured.given;

import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ADD_Delete_LibraryBook {

	static String S_id;
	public static void main(String[] args) {
		//String id = null;
		//String id = null;
		//addBook(id);
		
		addBook();
		System.out.println(S_id);
		GetBooks();
		DeleteBooks();
		//String delid=addBook(id);
	}
	
	public static void addBook() {
		RestAssured.baseURI="http://216.10.245.166";
	String resp=given().log().all().header("Content-Type","application/json")
		.body(payload.AddLib("bk","590")).when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
	JsonPath js=new JsonPath(resp);
	String id=js.getString("ID");
	S_id=id;
	
	}
	public static void GetBooks()
	{
	
		RestAssured.baseURI="http://216.10.245.166";
		String GetID=given().log().all().queryParam("ID",S_id ).header("Content-Type","application/json").when().get("/Library/GetBook.php")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=new JsonPath(GetID);
		String equal_addr=js1.getString("author");
		Assert.assertEquals("[John foe]", equal_addr);
		
	}
	public static void DeleteBooks()
	{
	
		RestAssured.baseURI="http://216.10.245.166";
		given().log().all().header("Content-Type","application/json")
				.body(payload.DeltBooks(S_id))
				.when().post("/Library/DeleteBook.php")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		
		
	}
}
