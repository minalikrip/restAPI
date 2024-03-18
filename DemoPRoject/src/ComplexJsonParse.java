import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js=new JsonPath(payload.CoursePrice());
		// Number of courses 
		int count = js.getInt("courses.size()");
		System.out.println(count);
		//Print Purchase Amount
		double amt=js.getDouble("dashboard.purchaseAmount");
		System.out.println(amt);
		//Print Title of first Course
		String title=js.getString("courses.title[0]");
		System.out.println(title);
		//Print All course titles and their respective Prices
		for(int i=0;i<count;i++)
		{
			String ts=js.getString("courses.title["+i+"]");
			double price=js.getDouble("courses.price["+i+"]");
			System.out.println(ts+" "+price);
		}
		//Print no of copies sold by RPA Course
		for(int i=0;i<count;i++)
		{
			String ts1=js.getString("courses.title["+i+"]");
			if(ts1.equalsIgnoreCase("RPA"))
			{
				int ncopies=js.get("courses.copies["+i+"]");
				System.out.println(ncopies);
				break;
			}
		}
		//Verify if Sum of all Course prices matches with Purchase Amount
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
		//Assert.assertEquals(sum, 789);
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
