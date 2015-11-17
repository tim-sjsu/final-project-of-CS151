import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseConnection 
{
	private static double moneyFromVendingMachine = 0;
	private  PreparedStatement p = null;
	Connection conn;
	
	public DatabaseConnection()
	{
		
	}
	
	//return the connection 
	public Connection getConnection() throws Exception
	{
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		String userName = "Tim";		// userName in mysql of user Tim	
		String password = "1234";		// password in mysql
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/student",userName, password);//student is database		
		return con;	 
	}	
	
	// this method get the string the password that match related input ID
	public String getPassword(int inputID) throws Exception
	{
		String password = null;
		Statement st = getConnection().createStatement();
		String sql = "select * from Student ";		// from table Student
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{		
			// check Student ID and password 
			if(inputID == rs.getInt(2))		
			{
				password = rs.getString(3);
			}
		}
		getConnection().close();		// close the connection
		return password;
	}
	
	
	//this method get the balance of student that matched related input ID
	public double getBalance(int inputID) throws Exception
	{
		double balance = 0;
		Statement st = getConnection().createStatement();
		String sql = "select * from Student ";		// from table Student
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{		
			// check Student ID and password 
			if(inputID == rs.getInt(2))		
			{
				balance = rs.getDouble(4);
			}
		}
		getConnection().close();		// close the connection
		return balance;
	}
	
	
	// displays the data of Student table 
	public void getStudentData(int input, String password) throws Exception
	{
		// get connection and create a statement
		Statement st = getConnection().createStatement();
		String sql = "select * from Student ";		// from table Student
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{		
			// check Student ID and password 
			if(input == rs.getInt(2) && password.equals((Object)rs.getString(3)))		
			{
				System.out.println("Name: "+ rs.getString(1) + " ID:" + rs.getString(2) + "\nYou have "+ rs.getDouble(4) + " dollars");
			}
		}
		getConnection().close();		// close the connection
	}
	
	
	//check whether still items leave in the database.
	public boolean checkItemsLeave(int itemID) throws Exception
	{
		boolean check = true;
		Statement st = getConnection().createStatement();
		String sql = "select * from items ";	// table items
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{		
			// check Student ID and get data 
			if(itemID == rs.getInt(2))		
			{
				if(rs.getInt(4) < 0)	// if no item leaves, return false
					check = false;
			}
		}
		getConnection().close();		// close the connection	
		return check;
	}
	
	// get the data of items Table,   @input is the item ID that selected by user
	public double getItemsData(int input) throws Exception
	{
		double price = 0;
		Statement st = getConnection().createStatement();
		String sql = "select * from items ";	// table items
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{		
			// check Student ID and get data 
			if(input == rs.getInt(2))		
			{
				price = rs.getDouble(3);
				//System.out.println(rs.getDouble(3));
			}
		}
		getConnection().close();		// close the connection
		return price;
	}
	public void getMoneyBoxData(int input) throws Exception
	{
		Statement st = getConnection().createStatement();
		String sql = "select * from MoneyBox";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{		
			// check Student ID and get data 
			if(input == rs.getInt(1))		
			{
				System.out.println(rs.getDouble(2));
			}
		}
		getConnection().close();		// close the connection
	}

	
	// output is the index of data,  newData is the new balance to be updated.
	public void updateStudent(int IDnumber, double newData) throws SQLException, Exception
	{
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		String sql = "select * from Student ";
		ResultSet rs = st.executeQuery(sql);
		int whereWhat = 0;
		Double setWhat = null;
		while(rs.next())
		{	
			
			// check Student ID and get data 
			if(IDnumber == rs.getInt(2))		//find a correct ID
			{
				whereWhat =rs.getInt(2);
				setWhat = newData;
			}
		}
		// update statement of SQL
		String query = "UPDATE Student SET balance = " + setWhat + " WHERE ID = " + whereWhat;	// set the change				
		p = (PreparedStatement) conn.prepareStatement(query);
		p.executeUpdate();
		p.close();
		conn.close();
	}	
	
	// this method only update the data of number of item leave.
	// when the transaction is finished, this should be updated.
	public void updateItems(int IDnumber) throws SQLException, Exception
	{
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		String sql = "select * from items ";
		ResultSet rs = st.executeQuery(sql);
		int whereWhat = 0;	// this variable is for SQL statement
		int setWhat = 0;	// this variable is for SQL statement
		while(rs.next())
		{		
			// check Student ID and get data 
			if(IDnumber == rs.getInt(2))		//find a correct ID
			{
				whereWhat =rs.getInt(2);	// get a correct item ID
				setWhat = rs.getInt(4) - 1;	// every time minus 1 if the transaction is successful
			}
		}
		
		// update statement of SQL
		String query = "UPDATE items SET NumberOfLeave = " + setWhat + " WHERE itemID = " + whereWhat;	// set the change				
		p = (PreparedStatement) conn.prepareStatement(query);
		p.executeUpdate();
		p.close();
		conn.close();
	}
	
	
	// this method only works for vendingMachine, when vendingMachine updates the money box with this method, it will update into datebase
	// @methodFromVendingMoneyBox is the total money that vendingMachine provide, then vendingMachine's moneyBox will clear 0. 
	public void updateMoneyBox(int IDnumber, double methodFromVendingingMoneyBox) throws SQLException, Exception
	{
		moneyFromVendingMachine += methodFromVendingingMoneyBox;
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		String sql = "select * from MoneyBox ";		// database MoneyBox
		ResultSet rs = st.executeQuery(sql);
		int whereWhat = 0;
		Double setWhat = null;
		while(rs.next())
		{				
			// check Student ID and get data 
			if(IDnumber == rs.getInt(1))		//find a correct ID
			{
				whereWhat = rs.getInt(1);
				setWhat = moneyFromVendingMachine;
			}
		}
		// update statement of SQL
		String query = "UPDATE MoneyBox SET balance = " + setWhat + " WHERE VendMachineID = " + whereWhat;	// set the change				
		p = (PreparedStatement) conn.prepareStatement(query);
		p.executeUpdate();
		p.close();
		conn.close();
	}
}
