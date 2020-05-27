package Server;

import Server.Actions.Action;
import Server.Actions.InvalidAction;
import Shared.Network.*;

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
	dbServer db;
	ArrayList<Token> tokens = new ArrayList<Token>();
	ArrayList<Action> actions = new ArrayList<Action>();

	/**
	 * SocketHandler Constructor
	 * @param inputSocket The ServerSocket to run on
	 * @param inputDB The database instance
	 */
	public SocketHandler(ServerSocket inputSocket, dbServer inputDB) {
		serverSocket = inputSocket;
		db = inputDB;
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
				System.out.println(tokens);
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
	private Response CalculateResponse(Request request) throws IOException, ClassNotFoundException {
		Action invokedAction = null;
		System.out.printf("Received request for action: %s", request.getAction().toString());

		// Search for the requested action
		for (Action a : actions) {
			if (a.associatedAction == request.getAction()) {
				invokedAction = a;
			}
		}

		if (invokedAction == null) {
			invokedAction = new InvalidAction();
		}

		return invokedAction.Run(request);

		/* switch (request.getEndpoint()) {
			case "echo": {
				if (tokens.contains(request.getToken())) {
					if (request.getToken().getExpires() >= Instant.now().getEpochSecond()) {
						return new Response("success", request.getData(), resetExpire(request.getToken()));
					} else {
						removeToken(request.getToken());
						return new Response("error", "Expired token", null);
					}
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
					addToken(token);
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
					addToken(token);
					return new Response("success", token, token);
				} else {
					return new Response("error", "User already exists", null);
				}
			}
			case "logout": {
				if (tokens.contains(request.getToken())) {
					removeToken(request.getToken());
					return new Response("success", null, null);
				} else {
					return new Response("error", "Invalid token", null);
				}
			}
			case "getEvents": {
				if (tokens.contains(request.getToken())) {
					if (request.getToken().getExpires() >= Instant.now().getEpochSecond()) {
						ArrayList<Event> eventList = db.returnEventList();
						return new Response("success", eventList, resetExpire(request.getToken()));
					} else {
						removeToken(request.getToken());
						return new Response("error", "Expired token", null);
					}
				} else {
					return new Response("error", "Invalid token", null);
				}
			}
			default: {
				return new Response("error", "Endpoint not implemented", null);
			}
		} */
	}


	/**
	 * Checks if a token is valid or not. Provides a reason why the token was not accepted.
	 * If the token is valid, will renew it.
	 * If the token is expired, will remove it from the token array.
	 *
	 * @param token the token to examine
	 * @returns valid if usable, invalid or expired otherwise
	 */
	public TokenStatus validateToken(Token token) {
		if (tokens.contains(token)) {
			if (token.getExpires() >= Instant.now().getEpochSecond()) {
				// token is valid
				resetExpire(token);
				return TokenStatus.valid;
			} else {
				// token has expired
				removeToken(token);
				return TokenStatus.expired;
			}
		} else {
			// token is invalid
			return TokenStatus.invalid;
		}
	}

	private Token addToken(Token token) {
		tokens.add(token);
		return token;
	}

	private Token removeToken(Token token) {
		tokens.remove(token);
		return token;
	}

	public Token resetExpire(Token token) {
		Token tempToken = removeToken(token);
		addToken(tempToken.resetExpire());
		return tempToken;
	}

	public Token generateToken(String username) {
		// Generate a random array of bytes, and encode it to base64 text
		SecureRandom secureRandom = new SecureRandom();
		byte[] values = new byte[128];
		secureRandom.nextBytes(values);
		Base64.Encoder base64 = Base64.getEncoder();
		String encoded = new String(base64.encode(values));
		// Instantiate a new token object, store it, and return it
		Token token = new Token(username, Instant.now().getEpochSecond() + 86400, encoded);
		addToken(token);
		return token;
	}
}
