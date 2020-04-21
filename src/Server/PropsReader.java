package Server;

/**
 * PropsReader is short for Properties File Reader. This class handles reading the server properties file (db.props)
 * that contains a bunch of settings. The class will automatically read the file when instantiated, logging any errors.
 *
 * After that, you can query the various properties of this file.
 *
 * From task sheet:
 *	- Upon starting up it will read the port it is supposed to operate on from a Properties file. The Server will then
 *	listen on that port for connections from Billboard Viewer and Billboard Control Panel applications.
 *	- The Billboard Server will retrieve information about the database it is supposed to connect to from a Properties
 *	file named db.props.This includes the URL that JDBC is to connect on (jdbc.url), the database it will work from
 *	(jdbc.schema) and the username (jdbc.username) and password (jdbc.password) it will connect to the server with.
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
	 * @todo Figure out what this should be. Where does the file reader look by default?
	 */
	public static string filePath = "Props/";

	/** Name of the file that contains database connection info */
	private final static string databasePropsFileName = "db.props";

	/** Name of the file that contains server port info */
	private final static string serverPropsFileName = "port.props";

}
