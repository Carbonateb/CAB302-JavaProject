package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleClient {
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		System.out.println("Client Starting...");

		ServerPropsReader propsReader = new ServerPropsReader();
		Socket socket = new Socket("localhost", propsReader.GetPort());

		System.out.println("Connected to server...");

		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		String toSend = "Hello Server!";
		objectOutputStream.writeObject(toSend);
		objectOutputStream.flush();

		System.out.println("Sent: " + toSend);

		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

		Object reply = objectInputStream.readObject();
		System.out.println("Received: " + reply);

		socket.close();
	}

}
