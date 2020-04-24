package Shared;

public class ClientPropsReader extends PropsReader {

	/** Path to server.props file */
	private static final String clientPropsFileLocation = "ClientConfig/client.props";




	/** Constructor */
	ClientPropsReader(){
		super();
		ReadPropFile(clientPropsFileLocation);
	}
}
