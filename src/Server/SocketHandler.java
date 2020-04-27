package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler {

	ServerSocket serverSocket;

	public SocketHandler(ServerSocket inputSocket) {
		serverSocket = inputSocket;
	}

	public void Run() throws IOException, ClassNotFoundException {
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("Incoming connection from: " + socket.getInetAddress());

			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			Object input = objectInputStream.readObject();
			System.out.println("Received: " + input);

			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(input);
			System.out.println("Sent: " + input);

			socket.close();
		}
	}

}
