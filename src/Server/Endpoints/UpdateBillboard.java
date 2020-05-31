package Server.Endpoints;

import Shared.Billboard;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;

import java.io.IOException;

/***
 * endpoint to update a billboard in the database
 */
public class UpdateBillboard extends Endpoint {
	public UpdateBillboard(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.updateBillboard;

		requiredPermission = Perm.EDIT_ALL_BILLBOARDS;
	}

	/***
	 * end point for updateBillboard method which updates a billboard in the DB
	 * @param input contains data sent from client
	 * @return boolean
	 */
	public Object executeEndpoint(Request input){

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
