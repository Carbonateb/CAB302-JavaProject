package Server;

import Shared.Request;
import Shared.Response;

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

			// Read the request
			Object raw = objectInputStream.readObject();

			Request request = null;
			Response response = new Response("error", "An unknown error occurred");
			try {
				// Cast request to Request class
				request = (Request) raw;

				// Generate a response
				response = CalculateResponse(request);
			} catch (java.lang.ClassCastException e) {
				response = new Response("error", "Malformed request (should be class Response)");
			} finally {
				System.out.println("Received: " + request);
				objectOutputStream.writeObject(response);
				System.out.println("Sent: " + response);

				socket.close(); // End the connection
			}
		}
	}

	/**
	 * Generate a response for a given request
	 */
	private Response CalculateResponse(Request request) {
		switch (request.getEndpoint()) {
			case "echo":
				return new Response("success", request.getData());
			default:
				return new Response("error", "Endpoint not implemented");
		}
	}
}
