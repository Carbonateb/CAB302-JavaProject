package Server.Endpoints;

import Shared.Credentials;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Permissions.Perm;

public class UpdateCreateUser extends Endpoint {
	public UpdateCreateUser(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.updateCreateUser;

		requiredPermission = Perm.EDIT_USERS;
	}

	public Object executeEndpoint(Request request) {

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
				return username;
			} else {
				return new Response("error", "User already exists", null);
			}
		} catch (java.security.NoSuchAlgorithmException e) {
			return new Response("error", "Server error - SHA-256 not implemented", null);
		}
	}
}