package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class mysqlConnection {

	public static void main(String[] args) 
	{
		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
   	}
	
	public static ArrayList<String> getTable(String msg1,String username,String pass)
	{
		Statement stmt;
		String buff;
		ArrayList<String> returnVal = new ArrayList<String>();

		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test",username,pass);
			stmt = conn.createStatement();
			System.out.println("SQL connection succeed");
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + msg1 + ";");
	 		while(rs.next())
	 		{

	 				//add to return list
	 				returnVal.add(rs.getString(1)+"  "+ rs.getString(2)+"  "+ rs.getString(3));
	 				// Print out the values
			} 
			rs.close();
		} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println(buff="SQLException: " + ex.getMessage());
            System.out.println(buff+="SQLState: " + ex.getSQLState());
            System.out.println(buff+="VendorError: " + ex.getErrorCode());
            returnVal.add(buff);
            }
		finally{
			return returnVal;
		}
	}
	
	
	public static ArrayList<String> setTable(String tableName , String Data, String username, String pass)
	{
		Statement stmt;
		String buff;
		ArrayList<String> returnVal = new ArrayList<String>();
		String[] newRow = Data.split(",");
		
		/***********************************************************************************************/
		System.out.println("row 0 = "+ newRow[0]+ "\nrow 1 = " + newRow[1] + "\nrow 2 = " + newRow[2]);
		
		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test",username,pass);
			stmt = conn.createStatement();
			System.out.println("SQL connection succeed");
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");
	 		while(rs.next())
	 		{
	 			if(rs.getString(1).equals(newRow[0])  )
	 			{
	 				PreparedStatement PS = conn.prepareStatement("UPDATE "+ tableName +" SET Specialization = ? WHERE id = ?");
	 				PS.setString(1, newRow[2]);
	 				PS.setString(2,newRow[0]);
	 				PS.executeUpdate();
	 			}	
	 				
	 		} 
			rs.close();
			returnVal.add("Succeed");
		} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println(buff="SQLException: " + ex.getMessage());
            System.out.println(buff+="SQLState: " + ex.getSQLState());
            System.out.println(buff+="VendorError: " + ex.getErrorCode());
            returnVal.add("Error");
     	    }
		finally{
			return returnVal;
		}
	}

	
	public static void createTableCourses(Connection con1){
		Statement stmt;
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate("create table courses(num int, name VARCHAR(40), semestr VARCHAR(10));");
			stmt.executeUpdate("load data local infile \"courses.txt\" into table courses");
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
	
	
	
}


