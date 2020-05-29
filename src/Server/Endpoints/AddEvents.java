package Server.Endpoints;

	import Shared.Network.Request;
	import Shared.Network.Response;
	import Shared.Network.Token;
	import Shared.Permissions.Perm;
	import Shared.Schedule.Event;

	import java.io.IOException;

//!!UNTESTED!!

public class AddEvents extends Endpoint {
	public AddEvents(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.addEvents;
	}

	public Object executeEndpoint(Request input){

		Token token = input.getToken();
		if (!server.socketHandler.hasPerm(token.getUser(), Perm.SCHEDULE_BILLBOARDS)) {
			return new Response("error", "Permission denied", null);
		}

		Event event = (Event) input.getData();
		try {
			//adds an event to the schedule and database
			server.db.addEvent(event.startTime, event.endTime, event.billboardName, event.author);
			return true;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	}

}
