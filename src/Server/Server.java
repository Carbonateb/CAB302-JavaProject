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

		//add data to db

		db.addUser("dylan", "faljnfkan");
		db.addUser("colby", "gggggddd");
		db.addBillboard("hi", "red", "cat.png");
		db.addSchedule("12 30 00", "01 00 00");

		System.out.println("added");

		//query data from db

		String[] query = db.queryDB("USERS", "1", "usr_ID");
		String[] query2 = db.queryDB("BILLBOARDS", "1", "bb_ID");
		String[] query3 = db.queryDB("SCHEDULE", "1", "bb_ID");

		//print data from db

		for(int i=0; i< query.length; i++)
		{
			System.out.println(query[i]);
		}
		for(int i=0; i< query2.length; i++)
		{
			System.out.println(query2[i]);
		}
		for(int i=0; i< query3.length; i++)
		{
			System.out.println(query3[i]);
		}

		//remove data from db
		System.out.println("dropped");
		db.dropUser(1);
		db.dropUser(2);
		db.dropSchedule(1);
		db.dropBillboard(1);

		System.out.println("dropped");

		socketHandler.Run();
		db.closeResources();
	}
}
