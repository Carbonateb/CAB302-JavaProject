package Server;

import java.io.IOException;
import java.net.ServerSocket;
import Server.SocketHandler;

public class Server {
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		System.out.println("Server Starting...");

		PropsReader propsReader = new PropsReader();
		ServerSocket serverSocket = new ServerSocket(propsReader.GetPort());
		SocketHandler socketHandler = new SocketHandler(serverSocket);
		socketHandler.Run();
	}
}
