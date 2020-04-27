package Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server is a class which can receive requests and respond to them
 * along with make requests to the database
 *
 * @author Colby Derix n10475991
 * @author Dylan Robertson n10487310
 */
public class Server {
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		System.out.println("Server Starting...");
		dbServer db = new dbServer();

		// Define server socket and socket handler
		ServerPropsReader propsReader = new ServerPropsReader();
		ServerSocket serverSocket = new ServerSocket(propsReader.GetPort());
		SocketHandler socketHandler = new SocketHandler(serverSocket);

		db.setupDB();

		// Add data to DB
		db.addUser("dylan", "faljnfkan");
		db.addUser("colby", "gggggddd");
		db.addBillboard("hi", "red", "cat.png");
		db.addSchedule("12 30 00", "01 00 00");
		System.out.println("Added items");

		// Query data from DB
		String[] query = db.queryDB("USERS", "1", "usr_ID");
		String[] query2 = db.queryDB("BILLBOARDS", "1", "bb_ID");
		String[] query3 = db.queryDB("SCHEDULE", "1", "bb_ID");

		// Print data from DB
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

		// Remove data from DB
		db.rmUser(1);
		db.rmUser(2);
		db.rmSchedule(1);
		db.rmBillboard(1);

		System.out.println("Removed items");

		// Run socketHandler forever
		socketHandler.Run();

		// Close the DB
		db.closeResources();
	}
}
