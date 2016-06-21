package db;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ocsf.server.GHealthServer;
import views.ServerView;

import com.mysql.jdbc.ResultSetMetaData;


/* 
 * The connection should start and end every request. 
 * http://blog.shinetech.com/2007/08/04/how-to-close-jdbc-resources-properly-every-time/
 */


// for details on sqlBuilder - http://openhms.sourceforge.net/sqlbuilder/
public class SQLconnection {

	private static Connection conn;
	private String SQLusername;
	private String SQLpassword;
	private ServerView SV;
	private boolean connected;

	/**
	 * Tries to establish a connection with the SQL DB 
	 * and sets the 'offline'/'online' signs in the loginView.
	 * @param username sql user
	 * @param password sql password
	 */
	public SQLconnection(String username,String password)  {

		SQLusername = username;
		SQLpassword = password;
		SV = GHealthServer.ServerMasterCont.serverVCont.serverView;
		connected = connect();
		
		if(connected){
			SV.lblMysqlStateWarning("Online",Color.GREEN);
			System.out.println("SQL connected.  Username: " + SQLusername + " Password: " + SQLpassword);
		}
		else{
			System.err.println("SQLconnection Error");
			SV.lblMysqlStateWarning("Offline",Color.RED);
		}
		disconnect();
	}
	
	/** Make a connection with the DB*/
	private boolean connect(){
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ghealth",SQLusername,SQLpassword);
		}
		catch(Exception e)
		{
			//System.err.println("SQLconnection Error");
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	
	/** Close the connection with the DB*/
	private boolean disconnect(){
		try 
		{
			conn.close();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQL close.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * This method takes a string as a query and sends it
	 * to the SQL database, returning an <code>ArrayList</code>
	 * of objects filled with the returning fields in the table.
	 * 
	 * @param query
	 * @return ArrayList<Object>
	 */
	public ArrayList<Object> sendSqlQuery(String query){

		ArrayList<Object> arrList = new ArrayList<>();	
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData md;
		connected = connect();
		
		if (!connected)
			return null;
 
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			md = (ResultSetMetaData) rs.getMetaData();

			while(rs.next())	// rs -> rows, i -> columns
				for(int i = 1 ; i <= md.getColumnCount() ; i++) 
					arrList.add(rs.getObject(i));

			rs.close();
		}
		catch (SQLException e) 
		{
			System.err.println("SQLconnection in sendSqlQuery");
			e.printStackTrace();
		}
		
		disconnect();
		return arrList;
	}//sendSqlQuery
		
		


	public ArrayList<Object> sendSqlQueryWithDates(String query,Date from,Date to){

		ArrayList<Object> arrList = new ArrayList<>();	
		PreparedStatement stmt;
		ResultSet rs;
		ResultSetMetaData md;
		connected = connect();
		
		if (!connected)
			return null;
		
		try{
			stmt = conn.prepareStatement(query);
			stmt.setDate(1, from);
			stmt.setDate(2, to);
			rs = stmt.executeQuery();
			md = (ResultSetMetaData) rs.getMetaData();

			while(rs.next())	// rs -> rows, i -> columns
				for(int i = 1 ; i <= md.getColumnCount() ; i++)	
					arrList.add(rs.getObject(i));

			rs.close();
		}
		catch (SQLException e) 
		{
			System.err.println("SQLconnection in sendSqlQuery");
		}
		
		disconnect();
		
		return arrList;
	}
			
	


	/**
	 * This method sends an update to the SQL DB.
	 * An update may be: <strong>INSERT, UPDATE or 
	 * DELETE</strong> statements. 
	 * @param udpate
	 * @return
	 */
	public boolean sendSqlUpdate(String udpate){

		Statement stmt;
		boolean success = true;
		connected = connect();
		
		if (!connected)
			return false;
		
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(udpate);
		}
		catch (Exception e1) 
		{
			System.err.println("SQLconnection in sendSqlUpdate"); 
			//e1.printStackTrace();
			success = false;
		}
		disconnect();
		return success;
	}//sendSqlUpdate

	
	/** This method expects a string that is already
	 *  a prepared statement, and executes it.*/
	public void sendSqlPreparedStatment(PreparedStatement stmt){

//			connected = connect();
			
//			if (!connected)
//				return;
			
			try{
				stmt.execute();
				stmt.close();
			}
			catch (SQLException e) 
			{
				System.err.println("SQLconnection in sendSqlQuery");
				e.printStackTrace();
			}
	}
	
	
//	/** This method expects a string that is already
//	 *  a prepared statement, and executes it.*/
//	public void sendSqlPreparedStatment(String statment, Date from,Date to, Time Tfrom, Time Tto){
//
//			PreparedStatement stmt;
//			connected = connect();
//			
//			if (!connected)
//				return;
//			
//			try{
//				stmt = conn.prepareStatement(statment);
//				stmt.setDate(1, from);
//				stmt.setDate(2, to);
//				stmt.setTime(3, Tfrom);
//				stmt.setTime(4, Tto);
//			}
//			catch (SQLException e) 
//			{
//				System.err.println("SQLconnection in sendSqlQuery");
//			}
//	}


	/**
	 * Close the SQL Connection
	 */
	public void closeSqlConnection(){

		boolean catched = false;


		// crashes if the connection is close when method starts
		// from http://stackoverflow.com/questions/2225221/closing-database-connections-in-java
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			catched = true;
			SV.lblMysqlStateWarning("Online",Color.GREEN);
			//			e.printStackTrace();
		}
		if(!catched){
			SV.lblMysqlStateWarning("Offline",Color.RED);
			System.out.println("SQL Connection closed");
		}
	}//closeSqlConnection

	
	public Connection getConnectiono(){
		connect();
		return conn;
	}

	// sql data base info //
	public String getSQLpassword() {return SQLpassword;}
	public String getSQLusername() {return SQLusername;}
	public void setSQLpassword(String sQLpassword) {SQLpassword = sQLpassword;}
	public void setSQLusername(String sQLusername) {SQLusername = sQLusername;}
}



/*			MySQL Commands from lab07

mysql -u katerina

CREATE DATABASE mihlala;

SHOW DATABASES;

USE mihlala;

CREATE TABLE course(num INT, name VARCHAR(40), semestr VARCHAR(10));

SHOW TABLES;

DESCRIBE course;

CREATE TABLE professor(id INT, name VARCHAR(40));

CREATE TABLE teach(pid INT, cnum INT);

INSERT INTO professor VALUES(1, "Avi Soffer");

INSERT INTO professor VALUES(2, "Mati Golani");

SELECT * FROM professor;

LOAD DATA LOCAL INFILE "C:\\My Documents\\teaching\\DB_Mati\\seminar2_MySQL\\courses.txt" INTO TABLE course;

LOAD DATA LOCAL INFILE "C:\\My Documents\\teaching\\DB_Mati\\seminar2_MySQL\\teach.txt" INTO TABLE teach;

SELECT * FROM course;

UPDATE course SET semestr="Winter08-09" WHERE num=61309;

SELECT course.name, professor.name FROM course, professor, teach where course.num=teach.cnum and professor.id=teach.pid;


 */