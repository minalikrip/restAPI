

//import org.junit.Test;
import org.testng.annotations.DataProvider;

//import org.testng.annotations.Test;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import static io.restassured.RestAssured.*;

//import org.testng.annotations.Test;

public class LibraryAPI {
	
//ADD SINGLE BOOK
//	@Test
//	public void addBook() {
//		RestAssured.baseURI="http://216.10.245.166";
//	String resp=given().log().all().header("Content-Type","application/json")
//		.body(payload.AddLib("bca","900")).when().post("/Library/Addbook.php")
//		.then().log().all().assertThat().statusCode(200)
//		.extract().response().asString();
//	JsonPath js=new JsonPath(resp);
//	String id=js.getString("ID");
//	System.out.println(id);
//	}
	
	//ADD MULTIPLE BOOKS THROUGH DATA PROVIDER
	@Test(dataProvider="BookDetails")
	public void addMulBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String resp=given().log().all().header("Content-Type","application/json")
			.body(payload.AddLib(isbn,aisle)).when().post("/Library/Addbook.php")
			.then().log().all().assertThat().statusCode(200)
			.extract().response().asString();
		JsonPath js=new JsonPath(resp);
		String id=js.getString("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="BookDetails")
	public Object[][] getdata()
	{
		return new Object[][] {{"hosd","890"},{"kwji","39"},{"oihj","089"}};
	}
	//AFTER RUNNING ADD MULTIPLE BOOKS THEN ONLY DELETE MULTIPLE BOOKS SHOULD PERFORM
	@Test(dataProvider="BookDetails")
	public void DeleteMulBooks(String isbn, String aisle)
	{
		System.out.println(isbn+aisle);
		RestAssured.baseURI="http://216.10.245.166";
		given().log().all().queryParam("ID",isbn+aisle ).header("Content-Type","application/json")
		.body(payload.DeltBooks(isbn+aisle))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);		
	}
	

}
