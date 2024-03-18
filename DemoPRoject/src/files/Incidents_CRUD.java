package files;
import io.restassured.RestAssured;



import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.testng.Assert;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import files.payload;
public class Incidents_CRUD {

	public static String performRestOperation(HashMap<String, String> dataMap) throws IOException {
		// TODO Auto-generated method stub
		//RestAssured.baseURI="http://lin-oly-qa-001.centralus.cloudapp.azure.com:5107";
		RequestSpecification req;
		Response res = null;
		String URL = null;
		//1.Headers
		HashMap<String, String> headersMap = new HashMap();
		if(dataMap.containsKey("Headers")){
			String[] headers = dataMap.get("Headers").split(";");
			for (String header : headers){
				String[] keyValue=header.split(":");
				headersMap.put(keyValue[0], keyValue[1]);
			}
		}
		req = given().log().all().headers(headersMap);
		
		//2. Extendede URL
			if (dataMap.containsKey("filters"))
				URL = dataMap.get("resource") + dataMap.get("filters");
			else
				URL = dataMap.get("resource");
			System.out.println(URL);
		
		//3.Body
		if(dataMap.containsKey("Operation"))
		{
			if (dataMap.get("Operation").equalsIgnoreCase("POST")) 
			{
				res = req.when().body(dataMap.get("Payload")).when().post(URL).then().log().all().extract().response();
			}
			else if (dataMap.get("Operation").equalsIgnoreCase("GET")) 
			{
				res = req.when().get(URL).then().log().all().extract().response();
			}
			else if (dataMap.get("Operation").equalsIgnoreCase("PUT")) 
			{
				res = req.when().body(dataMap.get("Payload")).put().then().log().all().extract().response();
			}
			else if (dataMap.get("Operation").equalsIgnoreCase("DELETE")) 
			{
				res = req.when().body(dataMap.get("Payload")).delete(URL).then().log().all().extract().response();
			}
		}
			
		//5.StatusCode Verification
		if (dataMap.containsKey("StatusCode")) 
		{
			Assert.assertEquals(res.getStatusCode() + "", dataMap.get("StatusCode"));
			if (dataMap.get("StatusCode").equalsIgnoreCase(res.getStatusCode() + "")) {
				System.out.println("Status Code match");
			}
			else
			{
				System.out.println("Status Code doesn't match");
			}
			
		}
		//6. Verification
		//6.Data Verification with JPATH
				if (dataMap.containsKey("GetValueFromJpath")) 
				{
					String JpathValue;
					JpathValue= res.jsonPath().getString(dataMap.get("GetValueFromJpath"));
					return JpathValue;
				} 
				else if(dataMap.containsKey("GetResponseHeaders"))
				{
					return res.headers().toString();
				}
				else
					return res.asString();
		
		//Static Payload
//		   String response=given().log().all().header("Content-Type","application/json")
//				.body(payload.AddIncd()).when().post("/api/incidents/incident")
//				.then().log().all().assertThat().statusCode(200).extract().response().asString();
//		if(datamap.containsKey("Post_Operation"))
//		{
//		String response=given().log().all().header("Content-Type","application/json")
//				.body(payload()).when().post(datamap.get("resource"))///api/incidents/incident
//				.then().log().all().assertThat().statusCode(200).extract().response().asString();
//		}
		
		   
	}
	public static String Formatted_payload(HashMap<String, String> dataMap, String FileName) throws IOException {
		System.out.println(System.getProperty("user.dir"));
		String testUploadFile="C:\\Users\\mkriplan\\Documents\\payload_Files\\"+FileName;
				//"C:\\Users\\mkriplan\\Documents\\AddIncd.json";			
		String payload = new String(Files.readAllBytes(Paths.get(testUploadFile)));
		List<String> matches = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{\\{[^\\}]*\\}\\}");
		java.util.regex.Matcher matcher = pattern.matcher(payload);
		while (matcher.find()) {
			matches.add(matcher.group());
		}
		  Date date =  Calendar.getInstance().getTime();  
          DateFormat dateFormat = new SimpleDateFormat("yymmddhhmmss");  
          String strDate = dateFormat.format(date);  
		//long timeMilli = ds.getTime();
		for (String match : matches) {
			String variableName = match.replaceAll("\\{\\{", "").replaceAll("\\}\\}", "");
				payload = payload.replaceAll("\\{\\{" + variableName + "\\}\\}",strDate);
			
		}
		return payload;
		
	}

}
