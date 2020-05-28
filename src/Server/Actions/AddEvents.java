package Server.Actions;

	import Shared.Network.Request;
	import Shared.Schedule.Event;

	import java.io.IOException;
	import java.util.ArrayList;

	//!!UNTESTED!!

public class AddEvents extends Action {
	public AddEvents(){
		// This is the enum value bound to this action
		associatedAction = ActionType.addEvents;
	}

	public Object executeAction(Object input){
		Event event = (Event) input;
		try {
			//adds an event to the schedule and database
			server.db.addEvent(event.startTime, event.endTime, event.billboardID, event.author);
			return true;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	}

}
