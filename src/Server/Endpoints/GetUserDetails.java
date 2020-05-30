package Server.Endpoints;

import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;

public class GetUserDetails extends Endpoint {
	public GetUserDetails(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.getUserDetails;

		requiredPermission = Perm.EDIT_USERS;
	}

	/***
	 * end point for retrieving details for a specific user
	 * @param input
	 * @return Credentials class for the user (nulled password field)
	 */
	public Object executeEndpoint(Request input){
		return server.db.getUserDetails((String) input.getData());
	}

}
