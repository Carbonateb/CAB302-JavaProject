package Shared;

import java.io.Serializable;

/**
 * Request is a class which should be used to
 * store a username and (hashed) password
 *
 * @author Colby Derix n10475991
 */
public class Credentials implements Serializable {
	String _username;
	String _password;

	/**
	 * Credentials Constructor
	 * @param username The username
	 * @param password The (hashed) password
	 */
	public Credentials(String username, String password) {
		_username = username;
		_password = password;
	}

	public String getPassword() {
		return _password;
	}

	public String getUsername() {
		return _username;
	}

	public String toString() {
		return String.format("{username: \"%s\", password: \"%s\"}", _username, _password.substring(0,9) + "...");
	}
}
