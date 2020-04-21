package Server;

import java.io.*;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.HashMap;

/**
 * PropsReader is short for Properties File Reader. This class handles reading the server properties file (db.props)
 * that contains a bunch of settings. The class will automatically read the file when instantiated, logging any errors.
 * After that, you can query the various properties of this file via the provided functions.
 *
 * From task sheet:
 * - Upon starting up it will read the port it is supposed to operate on from a Properties file. The Server will then
 *   listen on that port for connections from Billboard Viewer and Billboard Control Panel applications.
 * - The Billboard Server will retrieve information about the database it is supposed to connect to from a Properties
 *   file named db.props. This includes the URL that JDBC is to connect on (jdbc.url), the database it will work from
 *   (jdbc.schema) and the username (jdbc.username) and password (jdbc.password) it will connect to the server with.
 *
 * That is:
 * - Port number to listen to
 * - URL to connect to
 * - Path to the database
 * - Username for server
 * - Password for server
 *
 * Note: It appears that they want us to create two files, one with port number, the other called db.props that contains
 * database info. To simplify things, both of these files are accessed via this class. They also don't specify a name
 * for the file with the port number, I'll call it port.props for now.
 *
 * TL;DR:
 * This class reads the .props files on init, which can then be queried at any time using the provided functions.
 *
 * @author Lucas Maldonado n10534342
 */
public final class PropsReader {

	/**
	 * Which folder to search for the .props files.
	 * Public static so that it's possible to change path via command line args
	 */
	public static String filePath = "/ServerConfig/";

	/** Name of the file that contains server port info */
	private final static String serverPropsFileName = "port.props";

	/** Name of the file that contains database connection info */
	private final static String databasePropsFileName = "db.props";

	/** Default values of serverPropsFile if some values are missing */
	private final static String[][] defaultServerProps = {
		{"port", "9977"}
	};

	/** Default values of databasePropsFile if some values are missing */
	private final static String[][] defaultDatabaseProps = {
		{"url", ""},
		{"path", ""},
		{"username", ""},
		{"password", ""},
	};

	/**
	 * @returns the 'port' value found in port.props
	 */
	public int GetPort()
	{
		try{
			return Integer.parseUnsignedInt(serverProps.get("port"));
		} catch (NumberFormatException e) {
			Log.warning("Can't parse Port number '" + e.getMessage() +
				"'. Falling back to default (" + defaultServerProps[0][1] + ")");
			return Integer.parseUnsignedInt(defaultServerProps[0][1]);
		}

	}

	/**
	 * @returns the 'url' value found in db.props
	 */
	public String GetURL()
	{
		return databaseProps.get("url");
	}

	/**
	 * @returns the 'path' value found in db.props
	 */
	public String GetPath()
	{
		return databaseProps.get("path");
	}

	/**
	 * @returns the 'username' value found in db.props
	 */
	public String GetUsername()
	{
		return databaseProps.get("username");
	}

	/**
	 * @returns the 'password' value found in db.props
	 */
	public String GetPassword()
	{
		return databaseProps.get("password");
	}

	// Variables for storing the properties
	private HashMap<String, String> serverProps = new HashMap<>();
	private HashMap<String, String> databaseProps = new HashMap<>();

	/** Log class for logging PropsReader specific messages with more helpful info */
	private static final Logger Log = Logger.getLogger(PropsReader.class.getName());


	/**
	 * Constructor that auto reads the properties files
	 * @author Lucas Maldonado n10534342
	 */
	public PropsReader() {
		Log.config("Current Working Directory is: " + GetCWD());

		boolean foundServerPropFile = true;
		boolean foundDatabasePropFile = true;

		// Doing them separately so that if one fails, the other file can still be read
		try{
			serverProps = ReadPropFile(GetCWD() + filePath + serverPropsFileName);
		} catch (Exception e) {
			Log.warning("Caught exception: " + e.getMessage() + "\nFalling back to default values");
			foundServerPropFile = false;
		}
		try{
			databaseProps = ReadPropFile(GetCWD() + filePath + databasePropsFileName);
		} catch (Exception e) {
			Log.warning("Caught exception: " + e.getMessage() + "\nFalling back to default values");
			foundDatabasePropFile = false;
		}

		// Ensure that all the data is present. Uses the default value arrays
		for (String[] s : defaultServerProps) {
			FixIfMissing(serverProps, s, serverPropsFileName, foundServerPropFile);
		}
		for (String[] s : defaultDatabaseProps) {
			FixIfMissing(databaseProps, s, databasePropsFileName, foundDatabasePropFile);
		}
	}

	/**
	 * Will guarantee that the provided key exists inside the map.
	 * It can also accept a default value, filling that out too if it's missing
	 *
	 * @param map the map to modify. Passed by reference.
	 * @param values contains the expected key and default value. key goes in [0], val in [1]
	 * @param sourceFile for the log, to say which file this key should be in.
	 * @param logMissingKeys if true, will print any missing keys to the log
	 * @author Lucas Maldonado n10534342
	 */
	private void FixIfMissing(HashMap<String, String> map, String[] values, String sourceFile, boolean logMissingKeys){
		if (!map.containsKey(values[0])){
			if (logMissingKeys) { Log.warning("Could not find '" + values[0] + "' key in file: " + sourceFile +
				". Adding key and using default value '" + values[1] + "'"); }
			map.put(values[0], values[1]);
		}
		else if(map.get(values[0]).isEmpty() && !values[1].isEmpty()) {
			Log.warning("The key '" + values[0] + "' has no value in file: " + sourceFile +
				". Falling back to default value '" + values[1] + "'");
			map.put(values[0], values[1]);
		}
	}

	/**
	 * Reads a .props file and returns a HashMap of keys and values.
	 * The .props file should be a list of keys and values, like this:
	 * 	username = admin
	 * 	password = p@ssw0rd
	 * 	this=works too
	 * 	and= values with = signs
	 * 	or: this
	 * That will then get placed into a HashMap for easy access.
	 * Note that the key (left side) will always be converted to lowercase.
	 *
	 * @author Lucas Maldonado n10534342
	 */
	private HashMap<String, String> ReadPropFile(String filePath) throws IOException {
		HashMap<String, String> ReturnValue = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		String line = reader.readLine();
		while (line != null){
			try{
				String[] splitLine = line.split(" ?=|: ?", 2);
				if (splitLine.length < 2) {
					throw new Exception(line);
				}
				ReturnValue.put(splitLine[0].toLowerCase(), splitLine[1].trim());
			}
			catch (Exception e) {
				// Badly formatted lines can be ignored, empty lines aren't reported at all
				if (!line.isEmpty()) {
					Log.info("Ignored badly formatted line: '" + e.getMessage() + "'");
				}
			}

			// Read next line, do this last
			line = reader.readLine();
		}
		reader.close();
		return ReturnValue;
	}

	/**
	 * Gets the Current Working Directory of the server. This is the absolute path to where the application was started.
	 * When debugging, it will return the folder in which the entire project is in, something like:
	 * C:\Users\maldo\Projects\CAB302-JavaProject
	 * Note that the final slash is absent, remember to add it in yourself.
	 * Also, Java uses backslashes internally (/), but will display forward slashes on Windows (like in example above).
	 */
	public static String GetCWD(){
		return System.getProperty("user.dir");
	}

}
