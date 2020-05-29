package Server.Endpoints;

import Shared.Network.Request;
import Shared.Network.Response;

public class Logout extends Endpoint {
	public Logout(){
		// This is the enum value bound to this action
		associatedEndpoint = EndpointType.logout;
	}

	public Response Run(Request request) {
		if (server.socketHandler.logout(request.getToken())) {
			return new Response("success", "Logged out successfully", null);
		} else {
			return new Response("error", "Invalid token", null);
		}
	}
}
