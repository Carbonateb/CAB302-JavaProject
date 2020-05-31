package Server.Endpoints;

import Shared.Network.Request;
import Shared.Permissions.Perm;
import Shared.Schedule.Event;

import java.util.logging.ConsoleHandler;

/***
 * end point for deleting a user from the database
 */
public class DeleteEvent extends Endpoint {
	public DeleteEvent(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.deleteEvent;

		requiredPermission = Perm.SCHEDULE_BILLBOARDS;
	}

	/***
	 * end point for deleting an event
	 * @param input
	 * @return request status
	 */
	public Object executeEndpoint(Request input) {

		try {
			server.db.rmEvent((Event) input.getData());
			return true;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}
}
