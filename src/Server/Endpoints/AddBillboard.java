package Server.Endpoints;

import Shared.Billboard;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;
import Shared.Schedule.Event;

import java.io.IOException;

public class AddBillboard extends Endpoint {
	public AddBillboard(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.addBillboard;
	}

	/***
	 * end point for addBillboard method which adds a billboard to the DB
	 * @param input contains data sent from client
	 * @return boolean
	 */
	public Object executeEndpoint(Request input){

		Token token = input.getToken();
		if (!server.socketHandler.hasPerm(token.getUser(), Perm.CREATE_BILLBOARDS)) {
			return new Response("error", "Permission denied", null);
		}

		Billboard billboard = (Billboard) input.getData();
		try {
			//adds an event to the schedule and database
			server.db.addBillboard(billboard);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
