package Server;

import Shared.PropsReader;

public final class ServerPropsReader extends PropsReader {

	/** Path to db.props file */
	private static final String dbPropsFileLocation = "ServerConfig/db.props";

	/** Path to server.props file */
	private static final String serverPropsFileLocation = "ServerConfig/server.props";


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
		dbPropsReader.ReadPropFile(dbPropsFileLocation);
		ReadPropFile(serverPropsFileLocation);
	}
}
