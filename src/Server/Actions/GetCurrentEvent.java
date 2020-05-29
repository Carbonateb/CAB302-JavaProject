package Server.Actions;

import Shared.Network.Request;

public class GetCurrentEvent extends Action {
	public GetCurrentEvent(){
		// This is the enum value bound to this action
		associatedAction = ActionType.getCurrentEvent;
	}


	public Object executeAction(Request input) {
		

		return input;
	}
}
