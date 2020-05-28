package Server;

import Shared.Billboard;
import Shared.Schedule.*;



import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

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
	Shared.Schedule.Schedule schedule = new Shared.Schedule.Schedule();

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
					"pw_Hash TEXT NOT NULL," +
					"salt TEXT NOT NULL UNIQUE"+
					");";

			String bb_sql =
				"CREATE TABLE IF NOT EXISTS BILLBOARDS (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					"data TEXT NOT NULL" +
					");";

			String schedule_sql =
				"CREATE TABLE IF NOT EXISTS SCHEDULE (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					"data TEXT NOT NULL" +
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
	 * Method that loads the saved database schedule to memory to edit later
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void loadScheduleToMem() throws IOException, ClassNotFoundException {
		String[] query3 = queryDB("SCHEDULE", "1", "id");

		Schedule schedule = new Schedule();
		System.out.println("should be empty : " + schedule.exportEvents().size());
		if (query3[1] != null) {
			Object obj = ObjectSerialization.fromString((query3[1]));
			schedule = (Schedule) obj;
		}
		System.out.println("after fetching db :" + schedule.exportEvents().size());

	}

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
	 * @return returns a list of all the events
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Event> returnEventList() throws IOException, ClassNotFoundException {
		ArrayList<Event> test = requestEvents();
		return test;
	}

	/***
	 *  adds an event to the schedule, and saves the schedule to the database
	 * @param startTime unix time for start
	 * @param endTime unix time for end
	 * @param bbId the id of the black board
	 * @param author the user who added the event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void addEvent(long startTime, long endTime, int bbId, String author) throws IOException, ClassNotFoundException {
		Event event = new Event(startTime,endTime, bbId, author);
		schedule.scheduleEvent(event);
		saveSchedule(schedule);
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

	/***
	 * checks if a user exists
	 * @param usr string containing username
	 * @return
	 */
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
	 *checks password hash and user
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
			column_size = 4;
		}
		else if (table_Name == "BILLBOARDS")
		{
			column_size = 2;
		}
		else if (table_Name == "SCHEDULE")
		{
			column_size = 2;
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
	public boolean addUser(String usr_Name, String pw_Hash, String salt)
	{
		String sql =
				"INSERT INTO USERS (usr_Name, pw_Hash, salt)" +
				"VALUES ( '" +usr_Name + "' , '" + pw_Hash + "' , '" + salt + "')";
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
	 * fetches schedule from database
	 * @return returns list of events (call returnEventList to get list)
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Event> requestEvents() throws IOException, ClassNotFoundException {
		String[] query3 = queryDB("SCHEDULE", "1", "id");
		System.out.println("QUERY LENGTH "+query3.length + " id = " +query3[0] + " string = " + query3[1]);
		Schedule schedule = new Schedule();
		if (query3[1] != null) {
			schedule = (Schedule) ObjectSerialization.fromString((query3[1]));
		}

		return schedule.exportEvents();
	}

	/***
	 * adds a billboard to the database
	 * @param billboard billboard object
	 * @return true if sql ran successfully else false
	 */
	public boolean addBillboard(Billboard billboard) throws IOException
	{
		String data = ObjectSerialization.toString((Serializable) billboard);
		String sql =
			"INSERT INTO BILLBOARDS (data)" +
			"VALUES ('" + data + "' )";
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
	 * @param schedule schedule object
	 * @return true if sql ran successfully else false
	 */
	public boolean saveSchedule(Schedule schedule) throws IOException
	{
		String data = ObjectSerialization.toString((Serializable) schedule);
		String sql =
			"INSERT OR REPLACE INTO SCHEDULE (id,data)" +
				"VALUES (1, '" + data + "')" ;
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


