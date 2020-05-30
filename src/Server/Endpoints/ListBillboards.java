package Server.Endpoints;

import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;

import java.util.ArrayList;

public class ListBillboards extends Endpoint {
	public ListBillboards(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.listBillboards;
	}

	/***
	 * end point for retrieving the list of all billboards from the database
	 * @param input
	 * @return ArrayList of billboards (String)
	 */
	public Object executeEndpoint(Request input){
		Token token = input.getToken();
		if (!server.socketHandler.hasPerm(token.getUser(), Perm.CREATE_BILLBOARDS)) {
			return new Response("error", "Permission denied", null);
		}

		return server.db.listBillboards();
	}

}
