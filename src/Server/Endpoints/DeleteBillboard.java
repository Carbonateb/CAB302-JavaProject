package Server.Endpoints;

import Shared.Billboard;
import Shared.Network.Request;
import Shared.Network.Response;
import Shared.Permissions.Perm;
import Shared.Permissions.Permissions;

import java.io.IOException;

/***
 * end point for deleting a user from the database
 */
public class DeleteBillboard extends Endpoint {
	public DeleteBillboard(){
		// This is the enum value bound to this endpoint
		associatedEndpoint = EndpointType.deleteBillboard;
	}

	/***
	 * end point for deleting a user
	 * @param input
	 * @return request status
	 */
	public Object executeEndpoint(Request input) {
//		return server.db.rmBillboard((String) input.getData());
		if (!server.db.checkBillboardExists((String) input.getData())) {
			return false;
		}
		Billboard billboard = null;
		try {
			billboard = server.db.requestBillboard((String) input.getData());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return false;
		}

		if (billboard.author.equals(input.getToken().getUser())) {
			// Deleting their own billboard
			try {
				// TODO: check if billboard is scheduled (and deny removal)
				server.db.rmBillboard((String) input.getData());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			Permissions permissions = new Permissions(server.db.getUserDetails(input.getToken().getUser()).getPermissions());
			// Editing another billboard
			if (permissions.hasPermission(Perm.EDIT_ALL_BILLBOARDS)) {
				try {
					// TODO: remove all scheduled events using this billboard
					server.db.rmBillboard((String) input.getData());
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			} else {
				return new Response("error", "Permission denied", null);
			}
		}
	}

}
