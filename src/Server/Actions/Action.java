package Server.Actions;

import Server.Server;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Network.TokenStatus;

/**
 * An Action is something that the server can be requested to do remotely.
 *
 * @author Lucas Maldonado n10534342
 */
public class Action {

	/** This is the action command used to access this action */
	public ActionType associatedAction;

	Server server;


	/** Constructor */
	public Action() {
		associatedAction = null;
	}


	/**
	 * Default implementation of Run just makes sure that the given key is valid
	 */
	public Response Run(Request request) {

		TokenStatus tokenStatus = server.socketHandler.validateToken(request.getToken());

		switch (tokenStatus) {
			case valid:
				return new Response("success", executeAction(request.getData()), request.getToken());
			case expired:
				return new Response("error", "Expired token", null);
			case invalid:
				return new Response("error", "Invalid token", null);
		}

		// This line should never be reached, but it's required for compilation
		return new Response("error", "Not quite sure what happened there", null);
	}


	/**
	 * Override this method to add functionality to your Action
	 */
	public Object executeAction(Object input){
		return "Action not implemented yet, but was called correctly";
	}


	/**
	 * Should only be called by server, equivalent to constructor
	 */
	public void init(Server inServer) {
		server = inServer;
		System.out.printf(" - Action '%s' is ready, access with 'ActionType.%s'\n", getClass().getSimpleName(), associatedAction.toString());
	}
}
