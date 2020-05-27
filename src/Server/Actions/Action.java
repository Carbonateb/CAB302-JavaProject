package Server.Actions;

import Server.Server;
import Server.SocketHandler;
import Shared.Network.Response;

/**
 * An Action is something that the server can be requested to do remotely.
 *
 * @author Lucas Maldonado n10534342
 */
public class Action {

	Server server;
	SocketHandler socketHandler;


	/**
	 * Default implementation of Run just makes sure that the given key is valid
	 */
	public Response Run() {
		System.out.println("Action was run!");
		return null;
	}


	/**
	 * Should only be called by server, equivalent to constructor
	 */
	public void init(Server inServer, SocketHandler inSocketHandler) {
		server = inServer;
		socketHandler = inSocketHandler;
		System.out.printf("Action '%s' is ready\n", getClass().toString());
	}
}
