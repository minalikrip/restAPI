package files;


import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

public class Incidents_Auth extends Incidents_CRUD {
	HashMap<String, String> dataMap = new HashMap<>();
	String payload = null;
	String addInc;
	String baseURL="http://lin-oly-qa-001.centralus.cloudapp.azure.com:5107";
	@Test
	public void CreateIncident() throws IOException
	{
		dataMap.put("resource",baseURL+"/api/incidents/incident");
		dataMap.put("Operation", "POST");
		dataMap.put("Headers", "Content-Type:application/json");
		dataMap.put("StatusCode", "200");
		payload = Formatted_payload(dataMap, "AddIncd.json");
		dataMap.put("Payload", payload);
		//dataMap.put("GetValueFromJpath","id");
		addInc=performRestOperation(dataMap);
		System.out.println(addInc);
	}
	
}
