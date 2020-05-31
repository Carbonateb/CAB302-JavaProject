package Server.Endpoints;

import Shared.Network.Request;
import Shared.Permissions.Perm;

/***
 * end point for deleting a user from the database
 */
public class DeleteBillboard extends Endpoint {
	public DeleteBillboard(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.deleteBillboard;

		requiredPermission = Perm.EDIT_ALL_BILLBOARDS;
	}

	/***
	 * end point for deleting a user
	 * @param input
	 * @return request status
	 */
	public Object executeEndpoint(Request input) {
		return server.db.rmBillboard((String) input.getData());
	}

}
