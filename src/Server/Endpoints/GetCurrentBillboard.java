package Server.Endpoints;

import Shared.Network.Request;

public class GetCurrentBillboard extends Endpoint {
	public GetCurrentBillboard(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.getCurrentBillboard;
	}


	public Object executeEndpoint(Request input) {
		try{
			return server.db.requestBillbaord(server.db.requestCurrentEvent().billboardName);
		} catch (Exception ex) {
			return null;
		}
	}
}
