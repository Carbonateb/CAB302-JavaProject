package Server;

import java.io.*;
import Shared.RequestSender;
import Shared.Token;
import Shared.Request;

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
		Token token = new Token("testUser", 0, "tokentoken123123");
		Request request = new Request(token,"echo", "hello world!");

		// Print the payload to console, send it to the server, amd print the response
		System.out.println("Sending: " + request);
		Object response = requestSender.SendData(request);
		System.out.println("Response: " + response);
	}
}
