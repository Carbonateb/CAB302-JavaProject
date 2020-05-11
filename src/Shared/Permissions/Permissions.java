package Shared.Permissions;

import java.util.EnumSet;

/**
 * Just a wrapper for a EnumSet of the Perm enum also included in the Permissions package.
 *
 * @author Lucas Maldonado n10534342
 */
public class Permissions {

	public boolean hasPermission(Perm PermissionToCheck){
		return PSet.contains(PermissionToCheck);
	}

	public boolean addPermission(Perm PermissionToAdd){
		return PSet.add(PermissionToAdd);
	}

	public boolean removePermission(Perm PermissionToRemove){
		return PSet.remove(PermissionToRemove);
	}

	private EnumSet<Perm> PSet = EnumSet.noneOf(Perm.class);
}
