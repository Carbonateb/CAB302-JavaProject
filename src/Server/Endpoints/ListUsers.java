package Server.Endpoints;

import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;
import Shared.Schedule.Event;

import java.io.IOException;
import java.util.ArrayList;

public class ListUsers extends Endpoint {
	public ListUsers(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.listUsers;
	}

	/***
	 * end point for retrieving the list of all users from the database
	 * @param input
	 * @return ArrayList of usernames (String)
	 */
	public Object executeEndpoint(Request input){
		Token token = input.getToken();
		if (!server.socketHandler.hasPerm(token.getUser(), Perm.EDIT_USERS)) {
			ArrayList<String> singleUser = new ArrayList<String>();
			singleUser.add(token.getUser());
			return singleUser;
		}

		return server.db.listUsers();
	}

}
