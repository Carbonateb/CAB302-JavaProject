package Server.Actions;

import Shared.Credentials;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;

public class Register extends Action{
	public Register(){
		// This is the enum value bound to this action
		associatedAction = ActionType.register;
	}

	public Response Run(Request request) {
		Credentials credentials = null;
		try {
			// Cast request data to Credentials class
			credentials = (Credentials) request.getData();
		} catch (java.lang.ClassCastException e) {
			return new Response("error", "Malformed request (data property should be class Credentials)", null);
		}
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (true) { // TODO: Check that user does not already exist
			// TODO: Add user to DB
			Token token = server.socketHandler.generateToken(username);
			return new Response("success", token, token);
		} else {
			return new Response("error", "User already exists", null);
		}
	}
}
