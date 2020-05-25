package Shared;

import java.io.*;
import java.net.Socket;

/**
 * RequestSender is a class which can send requests to the IP and port provided
 * in the constructor. The class will also return the response from the server.
 *
 * @author Colby Derix n10475991
 */
public class RequestSender {

	String ipAddress;
	int port;
	Token token;

	/**
	 * RequestSender Constructor
	 * @param serverIP The IP address of the target server (recommended: 'localhost')
	 * @param serverPort The port of the target server (recommended: 9977)
	 */
	public RequestSender(String serverIP, int serverPort) {
		ipAddress = serverIP;
		port = serverPort;
		token = null;
	}

	/**
	 * Sends data to the server, and returns the response provided by the server
	 * @param endpoint The endpoint at which to query the server
	 * @param data The data that is to be sent
	 */
	public Response SendData(String endpoint, Object data) throws IOException, ClassNotFoundException {
		// Connect to server
		Socket socket = new Socket(ipAddress, port);

		// Setup streams
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		// Define payload
		Request request = new Request(token, endpoint, data);

		// Send the payload
		objectOutputStream.writeObject(request);
		objectOutputStream.flush();

		// Read the response
		Response response = (Response) objectInputStream.readObject();

		// Update the locally stored token
		if (response.getNewToken() != null) {
			token = response.getNewToken();
		}

		// Close the connection
		socket.close();

		// Return the server's response
		return response;
	}

	public Token getToken() {
		return token;
	}

	public String toString() {
		return String.format("{ipAddress: \"%s\", port: %d, token: \"%s\"}", ipAddress, port, token);
	}

	public String toString(String endpoint, Object data) {
		return String.format("{token: \"%s\", endpoint: \"%s\", data: \"%s\"}", token, endpoint, data);
	}
}
