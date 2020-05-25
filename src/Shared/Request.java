package Shared;

import java.io.Serializable;

/**
 * Request is a class which should be used when
 * sending requests to the server.
 *
 * @author Colby Derix n10475991
 */
public class Request implements Serializable {
	Token _token;
	String _endpoint;
	Object _data;

	/**
	 * Request Constructor
	 * @param token The users's token (`null` for users who are not logged in)
	 * @param endpoint Which endpoint on the server to query
	 * @param data The raw data the server will receive
	 */
	public Request(Token token, String endpoint, Object data) {
		_token = token;
		_endpoint = endpoint;
		_data = data;
	}

	public Token getToken() {
		return _token;
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public Object getData() {
		return _data;
	}

	public String toString() {
		return String.format("{token: \"%s\", expires: \"%s\", data: \"%s\"}", _token, _endpoint, _data);
	}
}
