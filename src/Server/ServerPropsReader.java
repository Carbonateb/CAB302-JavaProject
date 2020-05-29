package Server;

import Shared.PropsReader;

public final class ServerPropsReader extends PropsReader {

	/** Path to both .props files */
	private static final String propsFileLocation = "ServerConfig/";

	/** Path to db.props file */
	private static final String dbPropsFileName = "db.props";

	/** Path to db.props file */
	private static final String serverPropsFileName = "server.props";



	public int GetPort(){
		return GetIntProperty("port", 9977);
	}

	public String GetURL(){
		return dbPropsReader.GetStringProperty("jdbc.URL", "jdbc:mysql://localhost:3306");
	}

	public String GetSchema(){
		return dbPropsReader.GetStringProperty("jdbc.schema", "cab302");
	}

	public String GetUsername(){
		return dbPropsReader.GetStringProperty("jdbc.username", "root");
	}

	public String GetPassword(){
		return dbPropsReader.GetStringProperty("jdbc.password", "root");
	}


	/** Create another props reader for the db file */
	private PropsReader dbPropsReader = new PropsReader();

	/** Constructor */
	ServerPropsReader(){
		super();
		defaultKeysValues.put("port", "9977");
		dbPropsReader.defaultKeysValues.put("jdbc.URL", "jdbc:mysql://localhost:3306");
		dbPropsReader.defaultKeysValues.put("jdbc.schema", "cab302");
		dbPropsReader.defaultKeysValues.put("jdbc.username", "root");
		dbPropsReader.defaultKeysValues.put("jdbc.password", "root");
		dbPropsReader.ReadPropFile(propsFileLocation, dbPropsFileName);
		ReadPropFile(propsFileLocation, serverPropsFileName);
	}
}
