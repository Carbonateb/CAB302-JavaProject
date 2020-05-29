package Server.Endpoints;

import Shared.Network.Request;

public class GetCurrentBillboard extends Endpoint {
	public GetCurrentBillboard(){
		// This is the enum value bound to this action
		associatedEndpoint = EndpointType.getCurrentBillboard;
	}


	public Object executeAction(Request input) {
		try{
			return server.db.requestBillbaord(server.db.requestCurrentEvent().billboardName);
		} catch (Exception ex) {
			return null;
		}
	}
}
