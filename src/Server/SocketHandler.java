package Server;

import Shared.Credentials;
import Shared.Request;
import Shared.Response;
import Shared.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

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
			case "login":
				Credentials credentials = null;
				try {
					// Cast request data to Credentials class
					credentials = (Credentials) request.getData();
				} catch (java.lang.ClassCastException e) {
					return new Response("error", "Malformed request (data property should be class Credentials)");
				}
				String username = credentials.getUsername();
				String password = credentials.getPassword();

				if (true) { // TODO: Replace with an actual DB check
					// Generate a random array of bytes, and encode it to base64 text
					SecureRandom secureRandom = new SecureRandom();
					byte[] values = new byte[128];
					secureRandom.nextBytes(values);
					Base64.Encoder base64 = Base64.getEncoder();
					String encoded = new String(base64.encode(values));

					// TODO: Store the token data/expiry in DB

					// Instantiate a new token object and return it
					Token token = new Token(username, Instant.now().getEpochSecond() + 86400, encoded);
					return new Response("success", token);
				} else {
					return new Response("error", "Invalid credentials");
				}
			default:
				return new Response("error", "Endpoint not implemented");
		}
	}
}
