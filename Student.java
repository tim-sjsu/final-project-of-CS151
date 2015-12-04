import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class Student {	
	private static DatabaseConnection DB;
	public Student(DatabaseConnection dc)
	{
	      //DB = new DatabaseConnection();
		DB = dc;
	}
	
	//this is the tester for student, remember delete and delete "static" in the other methods in this class into
	/*public static void main(String [] args) throws Exception
	{
		DB = new DatabaseConnection();
		String StudentID = "1";			// just for test, if you use GUI, remember delete
		String inputPW = "tim1";		// just for test, if you use GUI, remember delete, this's should 
		double costMoney = 2;
		if (checkID(StudentID,inputPW))		// if user enter correct ID and password
		{
			System.out.println("Welcome!" + getStudentInfo(StudentID, inputPW));
			if(pay(StudentID, costMoney))	// if checkout successful
			{
				System.out.println("Thank you for your buying");
				System.out.println("here's your new balance information:" + getStudentInfo(StudentID, inputPW));
			}
		}
		else
		{
			System.out.print("Error ID or password");
		}
	}*/
	
	// this method check the ID and password whether matched in database
	// @input is the string of StudentID
	//@inputPW is the password of student
	public boolean checkID (String input, String inputPW) throws Exception
	{
		int inputID = Integer.parseInt(input);
		String thePW = DB.getStudentPassword(inputID);		// fix after initial DB
		if(inputPW.compareTo(thePW) == 0)
		{
			return true;
		}
		return false;
	}
	
	// this method implements the pay if the balance of student account greater than the cost of items
	//if yes, it returns true and update the new balance into database
	//if not, returns false
	// @inputBal is the cost of items,
	// @input is the ID number for String
	public boolean pay (String input, double inputBal) throws Exception
	{
		int inputID = Integer.parseInt(input);
		double remainBal= DB.getStudentBalance(inputID) - inputBal;	// new balance
		if(remainBal >= 0)		// fix after initial DB
		{
			DB.updateStudent( inputID, remainBal);	// update new balance into database
			return true;
		}
		return false;
	}
	
	// this method just return the balance of student with inputID
	// this method can delete if you want
	public double getBal(String input) throws Exception
	{
		
		int inputID = Integer.parseInt(input);
		if(DB.getStudentPassword(inputID)!=null)		// fix after initial DB
		{
			return DB.getStudentBalance(inputID);
		}
		
		return -1;
	}
	
	// this method get all the information except the student password in a string
	// if the user enters correct password, return
	// otherwise it outputs the wrong information
	//@input is the StudentID 
	// @ inputPW is the student password
	public String getStudentInfo(String input, String inputPW) throws Exception
	{
		int inputID = Integer.parseInt(input);
		String thePW = DB.getStudentPassword(inputID);		// fix after initial DB
		
		if(inputPW.equals(thePW))
		{
			return DB.getStudentData(inputID, thePW);		// fix after initial DB
		}
		
		else if(inputPW != thePW)
		{
			return "Wrong pass Word.";
		}
		return "ID Not Found";
	}
	
	
	
}