package Server.Endpoints;

import Shared.Credentials;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;
import Shared.Permissions.Perm;

public class Register extends Endpoint {
	public Register(){
		// This is the enum value bound to this action
		associatedEndpoint = EndpointType.register;
	}

	public Response Run(Request request) {

		Token token = request.getToken();
		if (!server.socketHandler.hasPerm(token.getUser(), Perm.EDIT_USERS)) {
			return new Response("error", "Permission denied", null);
		}

		Credentials credentials = null;
		try {
			// Cast request data to Credentials class
			credentials = (Credentials) request.getData();
		} catch (java.lang.ClassCastException e) {
			return new Response("error", "Malformed request (data property should be class Credentials)", null);
		}
		String username = credentials.getUsername();
		String password = credentials.getPassword();
		int permissions = credentials.getPermissions();

		try {
			if (!server.db.checkUserExists(username)) {
				server.db.addUser(username, password, permissions);
				return new Response("success", username, request.getToken());
			} else {
				return new Response("error", "User already exists", null);
			}
		} catch (java.security.NoSuchAlgorithmException e) {
			return new Response("error", "Server error - SHA-256 not implemented", null);
		}
	}
}
