package Server;

import java.io.*;
import Shared.RequestSender;

/**
 * ExampleClient is a class which can send a single request
 * and print the response provided back by a server.
 *
 * The intended use is debugging.
 *
 * @author Colby Derix n10475991
 */
public class ExampleClient {
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		// Define server address
		String serverIP = "localhost";
		int serverPort = 9977;

		// Create RequestSender
		RequestSender requestSender = new RequestSender(serverIP, serverPort);

		// Define payload
		Object toSend = "Hello Server!";

		// Print the payload to console, send it to the server, amd print the response
		System.out.println("Sending: " + toSend);
		Object response = requestSender.SendData(toSend);
		System.out.println("Response: " + response);

		// Example with an int...
		toSend = 99;
		System.out.println("Sending: " + toSend);
		response = requestSender.SendData(toSend);
		System.out.println("Response: " + response);
	}
}
