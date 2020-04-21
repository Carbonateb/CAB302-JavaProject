package Server;

import java.io.IOException;

public class Server {
	public static void main(String args[]) {
		System.out.println("Server starting");

		// Create the PropsReader for debugging purposes
		try {
			PropsReader testPropsReader = new PropsReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
