

import org.junit.Test;

import org.testng.Assert;
//import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	@Test
	public void SumOfCourses()
	{
		//Verify if Sum of all Course prices matches with Purchase Amount
		JsonPath js=new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		System.out.println(count);
		double amt=js.getDouble("dashboard.purchaseAmount");	
				System.out.println("Sum of all Course prices matches with Purchase Amount");
				double sum=0,tot_amt;
				for(int i=0;i<count;i++)
				{
					double prc=js.getDouble("courses.price["+i+"]");
					int ncopies=js.get("courses.copies["+i+"]");
					tot_amt=prc*ncopies;
					//System.out.println(tot_amt);
					sum+=tot_amt;	
					//System.out.println(sum);
				}
				System.out.println("Sum="+sum);
				Assert.assertEquals(sum, amt);
				if(sum==amt)
				{
					System.out.println("Sum Amount matches with Purchase Amount ");
				}
				else
				{
					System.out.println("Not Matched");
				}


	}
	

}
