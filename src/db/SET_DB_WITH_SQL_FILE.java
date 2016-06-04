package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.alee.laf.optionpane.WebOptionPane;

/**
 * <b>!!!!!!!!OVERWRITES YOUR CURRENT DB!!!!!!!</b></br>
 * Resets your DB with the SQL-start.sql file
 * in the OtherProjectFiles folder.
 * you can change to path to any local directory.</br> example: 
 * <code>"C:\\Giler\\Drive\\EclipseWS32\\G10_MyGas\\OtherProjectFiles\\SQL-start.sql"</code>
 * 
 * @from http://stackoverflow.com/a/1498029
 * 
 */
public class SET_DB_WITH_SQL_FILE {

	private static InputStream filePath = SET_DB_WITH_SQL_FILE.class.getResourceAsStream("/SQL-start.sql");
	
	private static Connection conn;
	private static String SQLusername = "root";
	private static String SQLpassword = "Braude";
	private static boolean catched;
	
	public static void main(String[] args) {
		
		System.out.println("Running SQL file to DB");
		try{

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/",SQLusername,SQLpassword);
			System.out.println("SQL connection succeed on constructor"); 

		}
		catch(Exception e){
			System.err.println("DB Connection Error!!!");
		} 
		
		try {
			InputStream inputstream = filePath;
			importSQL(conn,inputstream);
		} catch (SQLException e) {
			catched = true;
			e.printStackTrace();
		}
		
		if (!catched)
			 WebOptionPane.showMessageDialog ( null, "Data Base reset complete", "DB reset", WebOptionPane.INFORMATION_MESSAGE );
		else{WebOptionPane.showMessageDialog ( null, "Data Base reset failed! ", "DB reset", WebOptionPane.WARNING_MESSAGE );}


	}

	public static void importSQL(Connection conn, InputStream in) throws SQLException
	{
		Scanner s = new Scanner(in);
		s.useDelimiter("(;(\r)?\n)|(--\n)");
		Statement st = null;
		try
		{
			st = conn.createStatement();
			while (s.hasNext())
			{
				String line = s.next();
				if (line.startsWith("/*!") && line.endsWith("*/"))
				{
					int i = line.indexOf(' ');
					line = line.substring(i + 1, line.length() - " */".length());
				}

				if (line.trim().length() > 0)
				{
					st.execute(line);
				}
			}
		}
		finally
		{
			if (st != null) st.close();
		}
	}
}
