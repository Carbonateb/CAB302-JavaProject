package Server;

import java.sql.*;

public class dbServer {

	Connection cn = null;
	Statement stmt = null;
	ResultSet rs = null;


	//  Database credentials
	static String USER = "x";
	static String PASS = "x";

	/**
	 * set up the database
	 */

	public void setupDB() {
		//create reader object
		ServerPropsReader reader = new ServerPropsReader();
		USER = reader.GetUsername();
		PASS = reader.GetPassword();
		try
		{
			//Open a connection
			System.out.println("Connecting to a selected database...");
			//Class.forName("org.sqlite.JDBC");
			//Class.forName("org.sqlite.JDBC");
			cn = DriverManager.getConnection("jdbc:sqlite:main.db");
			stmt = cn.createStatement();

			System.out.println("Connected database successfully...");

			//create tables
			String usr_sql =
				"CREATE TABLE IF NOT EXISTS USERS (" +
					"usr_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					"usr_Name TEXT NOT NULL UNIQUE," +
					"pw_Hash TEXT NOT NULL" +
					");";

			String bb_sql =
				"CREATE TABLE IF NOT EXISTS BILLBOARDS (" +
					"bb_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					"bb_Text TEXT NOT NULL," +
					"bb_BGCol TEXT NOT NULL," +
					"bb_Img TEXT NOT NULL" +
					");";

			String schedule_sql =
				"CREATE TABLE IF NOT EXISTS SCHEDULE (" +
					"bb_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					"schedule_Time_Start TEXT NOT NULL," +
					"schedule_Time_End TEXT NOT NULL" +
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



	/***
	 *
	 * @param sql a string containing sql
	 * @return return true if sql ran successfully else return false
	 */

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

	/***
	 *
	 * @param sql the sql for the query in the form of a string
	 * @param column_size the column size of a given table
	 * @return array of strings of the queried data
	 */
	public String[] querySql(String sql, int column_size)
	{
		ResultSet rs;
		String[] stringArray = new String[column_size];

		Object obj;

		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				for(int j=0; j< column_size; j++)
				{
					obj = rs.getObject(j + 1);
					stringArray[j] = String.valueOf(obj);
				}
			}
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
		return stringArray;
	}


	public boolean checkUserExists(String usr)
	{
		String[] dbpw = queryDB("USERS", usr, "usr_Name");
		if( dbpw[1] != null)
		{
			if(dbpw[1].equals(usr))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	/***
	 *
	 * @param pw the password hash we are checking is valid
	 * @return boolean true if password is valid, false if not valid
	 */

	public boolean checkPassword(String pw, String usr)
	{
		String[] dbpw = queryDB("USERS", pw, "pw_Hash");
		if(dbpw[2] != null && dbpw[1] != null)
		{
			if(dbpw[2].equals(pw) && dbpw[1].equals(usr))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}

	}


	/***
	 *
	 * @param table_Name the name of the table you wish to pull from
	 * @param value the value of the primary key
	 * @param name the name of the primary key
	 * @return returns the queried data in the form of a string array
	 */
	public String[] queryDB(String table_Name, String value, String name)
	{
		value = "'"+value+"'";

		int column_size = 0;

		if(table_Name == "USERS")
		{
			column_size = 3;
		}
		else if (table_Name == "BILLBOARDS")
		{
			column_size = 4;
		}
		else if (table_Name == "SCHEDULE")
		{
			column_size = 3;
		}

		String sql =
			"SELECT * FROM " + table_Name + " WHERE " + name + " = " + value + ";";
		return querySql(sql, column_size);
	}


	/***
	 * adds a user to the database
	 * @param usr_Name string storing the username of the user
	 * @param pw_Hash string storing the hashed password of the user
	 * @return true if sql ran successfully else false
	 */
	public boolean addUser(String usr_Name, String pw_Hash)
	{
		String sql =
				"INSERT INTO USERS (usr_Name, pw_Hash)" +
				"VALUES ( '" +usr_Name + "' , '" + pw_Hash + "' )";
		return runSql(sql);
	}

	/***
	 * drops a user based on the users ID
	 * @param usr_ID integer storing the ID of the user (PK)
	 * @return true if sql ran successfully else false
	 */
	public boolean rmUser(int usr_ID)
	{
		String sql =
			"DELETE FROM USERS WHERE usr_ID = " + usr_ID ;
		return runSql(sql);
	}

	/***
	 * adds a billboard to the database
	 * @param bb_Text text to be displayed on the billboard
	 * @param bb_BGCol background color of the billboard
	 * @param bb_Img the url of the image to be displayed
	 * @return true if sql ran successfully else false
	 */
	public boolean addBillboard(String bb_Text, String bb_BGCol, String bb_Img)
	{
		String sql =
			"INSERT INTO BILLBOARDS (bb_Text, bb_BGCol, bb_Img)" +
			"VALUES ( '" + bb_Text + "' , '" + bb_BGCol + "' , '" + bb_Img + "' )";
		return runSql(sql);
	}

	/***
	 * removes a billboard from the database
	 * @param bb_ID unique identification for the billboard (PK)
	 * @return true if sql ran successfully else false
	 */
	public boolean rmBillboard(int bb_ID)
	{
		String sql =
			"DELETE FROM BILLBOARDS WHERE bb_ID = " + bb_ID ;
		return runSql(sql);
	}

	/***
	 * adds a schedule to the database
	 * @param schedule_Time_Start String containing the time the image is to be displayed
	 * @param schedule_Time_End String containing the time the image is to stop being displayed
	 * @return true if sql ran successfully else false
	 */
	public boolean addSchedule(String schedule_Time_Start, String schedule_Time_End)
	{
		String sql =
			"INSERT INTO SCHEDULE (schedule_Time_Start, schedule_Time_End)" +
				"VALUES ('" + schedule_Time_Start+ "' , '"+ schedule_Time_End +"')" ;
		return runSql(sql);
	}

	/***
	 * removes a schedule from the database
	 * @param bb_ID the id of the billboard the image will be displayed to(pk)
	 * @return true if sql ran successfully else false
	 */
	public boolean rmSchedule(int bb_ID)
	{
		String sql =
			"DELETE FROM SCHEDULE WHERE bb_ID = '" + bb_ID + "'";
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
				cn.close();
			}
		} catch (SQLException se) {
		}// do nothing
		try {
			if (cn != null) {
				cn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}//end finally try
	}

}


