package Server.Actions;

import Shared.Network.Request;

public class GetCurrentBillboard extends Action {
	public GetCurrentBillboard(){
		// This is the enum value bound to this action
		associatedAction = ActionType.getCurrentBillboard;
	}


	public Object executeAction(Request input) {
		return server.db.requestCurrentEvent();
	}
}
