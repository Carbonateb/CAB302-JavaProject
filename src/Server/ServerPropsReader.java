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
		return dbPropsReader.GetStringProperty("URL", "");
	}

	public String GetPath(){
		return dbPropsReader.GetStringProperty("path", "");
	}

	public String GetUsername(){
		return dbPropsReader.GetStringProperty("username", "");
	}

	public String GetPassword(){
		return dbPropsReader.GetStringProperty("password", "");
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
