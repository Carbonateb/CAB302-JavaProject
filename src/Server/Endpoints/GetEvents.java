package Server.Endpoints;

import Shared.Network.Request;
import Shared.Schedule.Event;

import java.io.IOException;
import java.util.ArrayList;

public class GetEvents extends Endpoint {
	public GetEvents(){
		// This is the enum value bound to this action
		associatedEndpoint = EndpointType.getEvents;
	}

	public Object executeAction(Request input){
		ArrayList<Event> eventList = null;
		try {
			eventList = server.db.returnEventList();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return eventList;
	}

}
