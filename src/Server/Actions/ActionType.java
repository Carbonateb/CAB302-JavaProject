package Server.Actions;

/**
 * Enum used to refer to the different action types.
 * This should mirror the list of available actions in Server.allActions.
 *
 * In case you're wondering, I did try to
 */
public enum ActionType {
	addEvents,
	echo,
	getCurrentBillboard,
	getEvents,
	login,
	logout,
	register,
}
