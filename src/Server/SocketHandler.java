package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SocketHandler is a class which - given a ServerSocket - can endlessly
 * process and respond to requests provided over the network.
 *
 * @author Colby Derix n10475991
 */
public class SocketHandler {

	ServerSocket serverSocket;

	/**
	 * SocketHandler Constructor
	 * @param inputSocket The ServerSocket to run on
	 */
	public SocketHandler(ServerSocket inputSocket) {
		serverSocket = inputSocket;
	}

	/**
	 * Endlessly process and respond to requests
	 */
	public void Run() throws IOException, ClassNotFoundException {
		while (true) { // Endlessly process connections
			Socket socket = serverSocket.accept(); // Open the connection
			System.out.println("Incoming connection from: " + socket.getInetAddress());

			// Setup streams
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			// Read the request and print it to the console
			Object request = objectInputStream.readObject();
			System.out.println("Received: " + request);

			// Generate a response, send it, and print it to the console
			Object response  = CalculateResponse(request);
			objectOutputStream.writeObject(response);
			System.out.println("Sent: " + response);

			socket.close(); // End the connection
		}
	}

	/**
	 * Generate a response for a given request
	 */
	private Object CalculateResponse(Object request) {
		return request; // Just echo back the request (for now)
	}

}
