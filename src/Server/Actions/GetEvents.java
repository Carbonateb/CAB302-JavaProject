package Server.Actions;

import Shared.Schedule.Event;

import java.io.IOException;
import java.util.ArrayList;

public class GetEvents extends Action {
	public GetEvents(){
		// This is the enum value bound to this action
		associatedAction = ActionType.getEvents;
	}

	public Object executeAction(Object input){
		ArrayList<Event> eventList = null;
		try {
			eventList = server.db.returnEventList();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return eventList;
	}

}
