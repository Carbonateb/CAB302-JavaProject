package Server;
import java.sql.*;
import java.time.LocalTime;
import java.util.Date;

public class dbServer {

	// JDBC driver name and database URL
	static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	static String DB_URL;

	//  Database credentials
	static String USER = "root";
	static String PASS = "root";

	Connection conn = null;
	Statement stmt = null;

	/**
	 * set up the database
	 */

	public void setupDB() {
		//create reader object
		PropsReader reader = new PropsReader();

		DB_URL = reader.GetURL() + "/" + reader.GetPath();

		try
		{
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			//create tables
			stmt = conn.createStatement();
			String usr_sql =
				"CREATE TABLE IF NOT EXISTS USERS (" +
					"usr_ID INT(11) NOT NULL PRIMARY KEY," +
					"usr_Name VARCHAR(20) NOT NULL," +
					"pw_Hash VARCHAR(20) NOT NULL" +
					");";

			String bb_sql =
				"CREATE TABLE IF NOT EXISTS BILLBOARDS (" +
					"bb_ID INT(11) NOT NULL PRIMARY KEY," +
					"bb_Text VARCHAR(20) NOT NULL," +
					"bb_BGCol VARCHAR(20) NOT NULL," +
					"bb_Img VARCHAR(100) NOT NULL" +
					");";

			String schedule_sql =
				"CREATE TABLE IF NOT EXISTS SCHEDULE (" +
					"schedule_Time VARCHAR(20) NOT NULL PRIMARY KEY," +
					"bb_ID INT(11) NOT NULL," +
					"CONSTRAINT schedule_fk FOREIGN KEY (bb_ID)" +
					"REFERENCES BILLBOARDS (bb_ID)" +
					");";

			stmt.executeUpdate(usr_sql);
			stmt.executeUpdate(bb_sql);
			stmt.executeUpdate(schedule_sql);
		}
		catch (SQLException se)
		{
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch (Exception e)
		{
			//Handle errors for Class.forName
			e.printStackTrace();
		}

	}//end main

	public boolean runSql(String sql)
	{
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (SQLException se)
		{
			//Handle errors for JDBC
			se.printStackTrace();
			return false;
		}
		catch (Exception e)
		{
			//Handle errors for Class.forName
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean addUser(int user_ID, String usr_Name, String pw_Hash)
	{
		String sql =
			"INSERT INTO USERS (usr_ID, usr_Name, pw_Hash)" +
				"VALUES ( '" + user_ID + "' , '" + usr_Name + "' , '" + pw_Hash + "' )";
		return runSql(sql);
	}

	public boolean dropUser(int user_ID)
	{
		String sql =
			"DELETE FROM USERS WHERE usr_ID = " + user_ID ;
		return runSql(sql);
	}

	public boolean addBillboard(int bb_ID, String bb_Text, String bb_BGCol, String bb_Img)
	{
		String sql =
			"INSERT INTO BILLBOARDS (bb_ID, bb_Text, bb_BGCol, bb_Img)" +
			"VALUES ( '" + bb_ID + "' , '" + bb_Text + "' , '" + bb_BGCol + "' , '" + bb_Img + "' )";
		return runSql(sql);
	}

	public boolean dropBillboard(int bb_ID)
	{
		String sql =
			"DELETE FROM BILLBOARDS WHERE bb_ID = " + bb_ID ;
		return runSql(sql);
	}

	public boolean addSchedule(String schedule_Time, int bb_ID)
	{
		String sql =
			"INSERT INTO SCHEDULE (schedule_Time, bb_ID)" +
				"VALUES ('" + schedule_Time + "' , (SELECT bb_ID FROM BILLBOARDS WHERE bb_ID = '" + bb_ID+ "'))" ;
		return runSql(sql);
	}

	public boolean dropSchedule(String schedule_Time)
	{
		String sql =
			"DELETE FROM SCHEDULE WHERE schedule_Time = '" + schedule_Time + "'";
		return runSql(sql);
	}

	/**
	 * close resources once finished with database
	 */
	public void closeResources()
	{
		//finally block used to close resources
		try {
			if (stmt != null) {
				conn.close();
			}
		} catch (SQLException se) {
		}// do nothing
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}//end finally try
	}

}


