package Shared;

import java.io.Serializable;

/**
 * Token is a class which represents a user's
 * authentication token.
 *
 * @author Colby Derix n10475991
 */
public class Token implements Serializable {
	String _user;
	long _expires;
	String _data;

	/**
	 * Token Constructor
	 * @param user The users's name
	 * @param expires The timestamp at which the token expires
	 * @param data The raw data the server will receive
	 */
	public Token(String user, long expires, String data) {
		_user = user;
		_expires = expires;
		_data = data;
	}

	public String getUser() {
		return _user;
	}

	public long getExpires() {
		return _expires;
	}

	public String getData() {
		return _data;
	}

	public String toString() {
		return String.format("{user: \"%s\", expires: %d, data: \"%s\"}", _user, _expires, _data.substring(0,9) + "...");
	}
}
