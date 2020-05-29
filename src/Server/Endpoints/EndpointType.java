package Server.Endpoints;

/**
 * Enum used to refer to the different action types.
 * This should mirror the list of available actions in Server.allEndpoints.
 *
 * In case you're wondering, I did try to use the endpoint class itself, but java doesn't play well with those
 */
public enum EndpointType {
	addEvents,
	echo,
	getCurrentBillboard,
	getEvents,
	listUsers,
	login,
	logout,
	register,
}
