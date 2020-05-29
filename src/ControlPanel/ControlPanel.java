package ControlPanel;

import Shared.ClientPropsReader;
import Shared.Network.RequestSender;
import Shared.Network.Response;
import Shared.Network.Token;

import javax.swing.*;

public class ControlPanel extends JFrame {


	public ClientPropsReader propsReader;
	public RequestSender requestSender;
	public Token sessionToken;


	/**
	 * Constructor of ControlPanel. Initialises many of the important classes, such as the PropsReader and
	 * RequestSender. The login process also starts from here.
	 */
	ControlPanel() {
		System.out.println("Starting control panel...");

		// Appears as the name of the application
		this.setTitle("Billboard Control Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up important classes
		propsReader = new ClientPropsReader();
		requestSender = new RequestSender(
			// Give the requestSender connection info from the .props file
			propsReader.getIPAddress(),
			propsReader.getPort()
		);


		// Create the login window and display it
		LoginWindow loginWindow = new LoginWindow();
		this.setContentPane(loginWindow.loginWindow);
		this.pack();
		this.setVisible(true);

		System.out.println("Waiting for user to log in...");
	}


	/**
	 * Called by the loginWindow once the user has successfully logged in.
	 * The server's response is saved so that we can keep the token it gives us.
	 * The main window is created here.
	 * @param serverMessage Response from the server to our login request
	 */
	public void loggedIn(Response serverMessage) {
		System.out.println("Logged in successfully. Control panel is ready!");

		sessionToken = serverMessage.getNewToken();

		// Create main window
		MainWindow mainWindow = new MainWindow();
		this.setContentPane(mainWindow.mainWindow);
		this.pack();
	}


	/**
	 * Entry point for the control panel. Just sets the system Look and Feel to something
	 * that is actually pleasant to the eyes, and creates an instance of ControlPanel.
	 * Take a look at the constructor if you're looking for the actual initialisation.
	 * @param args ControlPanel does not take in any command line arguments
	 */
	public static void main(String[] args) {
		// Set System L&F
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Failed to set the system look and feel!");
		}
		globalInstance = new ControlPanel();
	}

	/** Reference to the main instance of the ControlPanel. Access using the static get method */
	private static ControlPanel globalInstance;

	/**
	 * Gets the main Control Panel instance.
	 * If ControlPanel.main() was called, this will never be null.
	 * Use this to give callbacks back to ControlPanel.
	 * @returns the main control panel instance
	 */
	public static ControlPanel get() {
		return globalInstance;
	}
}
