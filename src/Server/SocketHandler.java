package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler {

	ServerSocket serverSocket;

	public SocketHandler(ServerSocket inputSocket) {
		serverSocket = inputSocket;
	}

	public void Run() throws IOException {
		while (true) {
			Socket socket = serverSocket.accept();

			InputStream inputStream = socket.getInputStream();

			int input = inputStream.read();
			System.out.println(input);

			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(input);

			socket.close();
		}
	}

}
