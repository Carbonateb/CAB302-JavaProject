package Shared;

import java.io.Serializable;

/**
 * Response is a class which is returned after
 * sending a request to the server.
 *
 * @author Colby Derix n10475991
 */
public class Response implements Serializable {
	String _status;
	Object _data;

	/**
	 * Request Constructor
	 * @param status The status of the request
	 * @param data The raw data that the server returned
	 */
	public Response(String status, Object data) {
		_status = status;
		_data = data;
	}

	public String getStatus() {
		return _status;
	}

	public Object getData() {
		return _data;
	}

	public String toString() {
		return String.format("{status: \"%s\", data: \"%s\"}", _status, _data);
	}
}
