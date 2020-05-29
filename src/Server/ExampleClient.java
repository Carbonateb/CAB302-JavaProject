package Server;

import Server.Actions.ActionType;
import Shared.Credentials;
import Shared.Network.Request;
import Shared.Network.RequestSender;
import Shared.Network.Response;
import Shared.Permissions.Permissions;
import Shared.Schedule.Event;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * ExampleClient is a class which can send a single request
 * and print the response provided back by a server.
 *
 * The intended use is debugging.
 *
 * @author Colby Derix n10475991
 */
public class ExampleClient {
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException, NoSuchAlgorithmException {
		// Define server address
		String serverIP = "localhost";
		int serverPort = 9977;

		Base64.Encoder base64 = Base64.getEncoder();
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		// Create RequestSender
		RequestSender requestSender = new RequestSender(serverIP, serverPort);

		// Define payload data
		String password = "secure_password";
		byte[] hash = md.digest(password.getBytes());

		String hashed_password = new String(base64.encode(hash));

		System.out.println(hashed_password);

		Credentials credentials = new Credentials("admin", hashed_password, null);
		// Login
		System.out.println("Logging in with: " + credentials);
		Response response = requestSender.login(credentials);
		System.out.println("Response: " + response);

		//Thread.sleep(1500);

		//Credentials newUser = new Credentials("colby", hashed_password, new Permissions(16));

		//System.out.println("Sending: " + requestSender.toString("register", newUser));
		//response = requestSender.SendData(ActionType.register, newUser);
		//System.out.println("Response: " + response);

		Thread.sleep(1500);

		// Print the payload to console, send it to the server, amd print the response
		System.out.println("Sending: " + requestSender.toString("echo", "hello world!"));
		response = requestSender.SendData(ActionType.echo, "hello world!");
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		System.out.println("Sending: " + requestSender.toString("GetEvents", null));
		response = requestSender.SendData(ActionType.getEvents, null);
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		System.out.println("Sending: " + requestSender.toString("AddEvents", null));
		Event eventObj = new Event(10000, 20000, 1, "hi");
		response = requestSender.SendData(ActionType.addEvents, eventObj);
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		System.out.println("Sending: " + requestSender.toString("GetEvents", null));
		response = requestSender.SendData(ActionType.getEvents, null);
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		// Logout
		System.out.println("Logging out");
		response = requestSender.logout();
		System.out.println("Response: " + response);
	}
}
