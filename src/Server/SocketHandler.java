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
import java.util.ArrayList;
import java.util.Base64;

/**
 * SocketHandler is a class which - given a ServerSocket - can endlessly
 * process and respond to requests provided over the network.
 *
 * @author Colby Derix n10475991
 */
public class SocketHandler {

	ServerSocket serverSocket;
	ArrayList<Token> tokens = new ArrayList<Token>();

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
			Response response = new Response("error", "An unknown error occurred", null);
			try {
				// Cast request to Request class
				request = (Request) raw;

				// Generate a response
				response = CalculateResponse(request);
			} catch (java.lang.ClassCastException e) {
				response = new Response("error", "Malformed request (should be class Response)", null);
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
			case "echo": {
				if (request.getToken() != null) { // TODO: Replace with actual token check
					return new Response("success", request.getData(), request.getToken().resetExpire());
				} else {
					return new Response("error", "Invalid token", null);
				}
			}
			case "login": {
				Credentials credentials = null;
				try {
					// Cast request data to Credentials class
					credentials = (Credentials) request.getData();
				} catch (java.lang.ClassCastException e) {
					return new Response("error", "Malformed request (data property should be class Credentials)", null);
				}
				String username = credentials.getUsername();
				String password = credentials.getPassword();

				if (true) { // TODO: Replace with an actual DB check
					String token_data = GenerateToken();

					// Instantiate a new token object, store it, and return it
					Token token = new Token(username, Instant.now().getEpochSecond() + 86400, token_data);
					tokens.add(token);
					return new Response("success", token, token);
				} else {
					return new Response("error", "Invalid credentials", null);
				}
			}
			case "register": {
				Credentials credentials = null;
				try {
					// Cast request data to Credentials class
					credentials = (Credentials) request.getData();
				} catch (java.lang.ClassCastException e) {
					return new Response("error", "Malformed request (data property should be class Credentials)", null);
				}
				String username = credentials.getUsername();
				String password = credentials.getPassword();

				if (true) { // TODO: Check that user does not already exist

					// TODO: Add user to DB

					String token_data = GenerateToken();

					// Instantiate a new token object, store it, and return it
					Token token = new Token(username, Instant.now().getEpochSecond() + 86400, token_data);
					tokens.add(token);
					return new Response("success", token, token);
				} else {
					return new Response("error", "User already exists", null);
				}
			}
			case "logout": {
				if (tokens.contains(request.getToken())) {
					tokens.remove(request.getToken());
					return new Response("success", null, null);
				} else {
					return new Response("error", "Invalid token", null);
				}
			}
			default: {
				return new Response("error", "Endpoint not implemented", null);
			}
		}
	}

	private String GenerateToken() {
		// Generate a random array of bytes, and encode it to base64 text
		SecureRandom secureRandom = new SecureRandom();
		byte[] values = new byte[128];
		secureRandom.nextBytes(values);
		Base64.Encoder base64 = Base64.getEncoder();
		String encoded = new String(base64.encode(values));

		return encoded;
	}
}
