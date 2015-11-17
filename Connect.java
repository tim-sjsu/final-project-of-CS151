import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.PreparedStatement;

public class Connect
{
	Connection conn;
	public static void main(String[] args) throws Exception
	{
		// this main method gives an example that how to use DatabaseConnection
		DatabaseConnection connect = new DatabaseConnection();
		int ID = 2;
		String password = "frank2";
		connect.getStudentData(ID,password);	
		System.out.print("Item " + ID + " is: " + connect.getItemsData(ID) + " dollars\n");
		//System.out.print("After transaction:");
		//connect.getStudentData(ID,password);
		System.out.print("moneyBox "+ ID + " has: ");
		connect.getMoneyBoxData(ID);
		connect.updateStudent(4, 123);
		connect.updateMoneyBox(1,10.0);
		connect.updateMoneyBox(1,65.4);
		connect.updateItems(1);
	}
}
