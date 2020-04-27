package Server;

import java.io.*;
import java.net.Socket;

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

		// This is the object that is sent
		Object toSend = "Hello Server!";

		// Connect to server
		System.out.println("Client Starting...");
		ServerPropsReader propsReader = new ServerPropsReader();
		Socket socket = new Socket("localhost", propsReader.GetPort());
		System.out.println("Connected to server!");

		// Setup streams
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		// Send the payload
		objectOutputStream.writeObject(toSend);
		objectOutputStream.flush();
		System.out.println("Sent: " + toSend);

		// Print the response
		Object response = objectInputStream.readObject();
		System.out.println("Received: " + response);

		socket.close(); // Close the connection
	}
}
