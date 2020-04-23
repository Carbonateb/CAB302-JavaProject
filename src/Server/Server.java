package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalTime;

import Server.ServerPropsReader;
import Server.SocketHandler;

public class Server {
	public static void main(String args[]) throws IOException {
		System.out.println("Server Starting...");
		dbServer db = new dbServer();

		ServerPropsReader propsReader = new ServerPropsReader();
		ServerSocket serverSocket = new ServerSocket(propsReader.GetPort());
		SocketHandler socketHandler = new SocketHandler(serverSocket);

		db.setupDB();

		db.addUser(321, "dylan", "faljnfkan");
		db.addBillboard(1, "hi", "red", "cat.png");
		db.addSchedule("22 07 24", 1);

		System.out.println("added");

		db.dropUser(321);
		db.dropSchedule("22 07 24");
		db.dropBillboard(1);

		System.out.println("dropped");

		socketHandler.Run();
		db.closeResources();
	}
}
