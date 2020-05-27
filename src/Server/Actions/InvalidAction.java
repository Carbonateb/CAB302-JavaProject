package Server.Actions;

import Shared.Network.Request;
import Shared.Network.Response;

/**
 * A simple action that says you stuffed up
 */
public class InvalidAction extends Action {
	public Response Run(Request request) {
		System.out.println("An invalid action was created! Ensure your Action was added to Sever.allActions");
		return new Response("error", "Invalid action", null);
	}
}
