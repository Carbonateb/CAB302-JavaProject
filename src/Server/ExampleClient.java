package Server;

import Shared.Credentials;
import Shared.Network.RequestSender;
import Shared.Network.Response;

import java.io.IOException;

/**
 * ExampleClient is a class which can send a single request
 * and print the response provided back by a server.
 *
 * The intended use is debugging.
 *
 * @author Colby Derix n10475991
 */
public class ExampleClient {
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		// Define server address
		String serverIP = "localhost";
		int serverPort = 9977;

		// Create RequestSender
		RequestSender requestSender = new RequestSender(serverIP, serverPort);

		// Define payload data
		Credentials credentials = new Credentials("test", "secure (hashed) password");
		// Login
		System.out.println("Logging in with: " + credentials);
		Response response = requestSender.login(credentials);
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		// Print the payload to console, send it to the server, amd print the response
		System.out.println("Sending: " + requestSender.toString("echo", "hello world!"));
		response = requestSender.SendData("echo", "hello world!");
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		System.out.println("Sending: " + requestSender.toString("getEvents", null));
		response = requestSender.SendData("getEvents", null);
		System.out.println("Response: " + response);

		Thread.sleep(1500);

		// Logout
		System.out.println("Logging out");
		response = requestSender.logout();
		System.out.println("Response: " + response);
	}
}
