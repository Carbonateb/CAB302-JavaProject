package Server;

import Shared.Billboard;
import Shared.Schedule.Event;
import Shared.Schedule.Schedule;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Server is a class which can receive requests and respond to them
 * along with make requests to the database
 *
 * @author Colby Derix n10475991
 * @author Dylan Robertson n10487310
 */
public class Server {


	public static void main(String args[]) throws IOException, ClassNotFoundException {
		dbServer db = new dbServer();

		db.setupDB();
		db.loadScheduleToMem();
		System.out.println("Server Starting...");


		// Define server socket and socket handler
		ServerPropsReader propsReader = new ServerPropsReader();
		ServerSocket serverSocket = new ServerSocket(propsReader.GetPort());
		SocketHandler socketHandler = new SocketHandler(serverSocket);



		// Add data to DB
		db.addUser("dylan", "faljnfkan", "salt1");
		db.addUser("colby", "gggggddd", "salt2");

		Billboard billboard = new Billboard(1, "sample", "sample2", Color.red, Color.blue, null, "admin");
		db.addBillboard(billboard);



		db.addEvent(10000000, 300000000, 1,"bob");
		db.addEvent(10000010, 300000001, 2,"jerry");

		ArrayList<Event> example = db.returnEventList();

		System.out.println("THE AUTHOR IS "+example.get(0).author);
		System.out.println("THE AUTHOR IS "+example.get(1).author);

		//System.out.println("THE START TIME IS" + db.requestBillBoard(1));

		System.out.println("Added items");

		if(db.checkPassword("gggggddd", "colby"))
		{
			System.out.println("password check for 'gggggddd' and usr colby present");
		}

		if(!db.checkPassword("faljnfkan", "lionblind"))
		{
			System.out.println("password check for 'faljnfkan' and user lionblind NOT PRESENT");
		}

		if(!db.checkPassword("pogchamp", "pepehands"))
		{
			System.out.println("password check for 'pogchamp' and user pepehands NOT PRESENT");
		}

		if(db.checkUserExists("colby"))
		{
			System.out.println("colby exists");
		}

		if(!db.checkUserExists("fred"))
		{
			System.out.println("fred does not exist");
		}


		// Query data from DB
		String[] query = db.queryDB("USERS", "1", "usr_ID");
		String[] query2 = db.queryDB("BILLBOARDS", "1", "id");
		String[] query3 = db.queryDB("SCHEDULE", "1", "id");

		// Print data from DB
		for(int i=0; i< query.length; i++)
		{
			System.out.println(query[i]);
		}
		for(int i=0; i< query2.length; i++)
		{
			if (i == 0)
			{
				System.out.println(query2[i]);
			}
			else
			{
				//System.out.println("billboard : " +ObjectSerialization.fromString(query2[i]));
				Object obj = ObjectSerialization.fromString((query2[i]));
				Billboard bbPrint = Billboard.class.cast(obj);

				System.out.println("id : " + bbPrint.ID);
			}
		}
		for(int i=0; i< query3.length; i++)
		{
			System.out.println(query3[i]);
		}

		// Remove data from DB
		//db.rmUser(1);
		//db.rmUser(2);
		//db.rmSchedule(1);
		//db.rmBillboard(1);

		System.out.println("Removed items");

		// Run socketHandler forever
		socketHandler.Run();

		// Close the DB
		db.closeResources();
	}
}
