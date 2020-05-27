package Server.Actions;

import Shared.Credentials;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.Token;

public class Login extends Action {
	public Login(){
		// This is the enum value bound to this action
		associatedAction = ActionType.login;
	}

	public Response Run(Request request) {
		Credentials credentials = null;
		try {
			// Cast request data to Credentials class
			credentials = (Credentials)request.getData();
		} catch (java.lang.ClassCastException e) {
			return new Response("error", "Malformed request (data property should be class Credentials)", null);
		}
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (true) { // TODO: Replace with an actual DB check
			Token token = server.socketHandler.generateToken(username);
			return new Response("success", token, token);
		} else {
			return new Response("error", "Invalid credentials", null);
		}
	}
}
