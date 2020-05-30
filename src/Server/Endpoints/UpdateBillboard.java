package Server.Endpoints;

import Shared.Billboard;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;

import java.io.IOException;

public class UpdateBillboard extends Endpoint {
	public UpdateBillboard(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.updateBillboard;
	}

	/***
	 * end point for addBillboard method which adds a billboard to the DB
	 * @param input contains data sent from client
	 * @return boolean
	 */
	public Object executeEndpoint(Request input){

		Token token = input.getToken();
		if (!server.socketHandler.hasPerm(token.getUser(), Perm.EDIT_ALL_BILLBOARDS)) {
			return new Response("error", "Permission denied", null);
		}

		Billboard billboard = (Billboard) input.getData();
		try {
			//adds an event to the schedule and database
			server.db.updateBillboard(billboard);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
