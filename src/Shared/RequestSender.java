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

	/**
	 * RequestSender Constructor
	 * @param serverIP The IP address of the target server (recommended: 'localhost')
	 * @param serverPort The port of the target server (recommended: 9977)
	 */
	public RequestSender(String serverIP, int serverPort) {
		ipAddress = serverIP;
		port = serverPort;
	}

	/**
	 * Sends data to the server, and returns the response provided by the server
	 * @param request The data that is to be sent
	 */
	public Object SendData(Request request) throws IOException, ClassNotFoundException {
		// Connect to server
		Socket socket = new Socket(ipAddress, port);

		// Setup streams
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		// Send the payload
		objectOutputStream.writeObject(request);
		objectOutputStream.flush();

		// Read the response
		Object response = objectInputStream.readObject();

		// Close the connection
		socket.close();

		// Return the server's response
		return response;
	}
}
