package ControlPanel;

import Server.Endpoints.EndpointType;
import Shared.ClientPropsReader;
import Shared.Network.RequestSender;
import Shared.Network.Response;
import Shared.Schedule.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Graphical User Interface for main window form
 */

public class ControlPanel extends JFrame {
	private JButton newBillboard_Button;
	private JButton editUsersButton;
	public JPanel mainPanel;
	private JTable schedule_Table;
	private JButton newEvent_Button;
	private JTable mainWindowTable;
	private JTextPane billboardControlPanelV0TextPane;

	// End UI Variables

	public ClientPropsReader propsReader;
	public RequestSender requestSender;


	/**
	 * Constructor of ControlPanel. Initialises many of the important classes, such as the PropsReader and
	 * RequestSender. The login process also starts from here.
	 */
	public ControlPanel() {
		System.out.println("Starting control panel...");

		// Appears as the name of the application
		setTitle("Billboard Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up important classes
		propsReader = new ClientPropsReader();
		requestSender = new RequestSender(
			// Give the requestSender connection info from the .props file
			propsReader.getIPAddress(),
			propsReader.getPort()
		);

		setVisible(true);
		// Create the login window and display it
		LoginWindow loginWindow = new LoginWindow();
		setContentPane(loginWindow.loginWindow);
		pack();
		System.out.println("Waiting for user to log in...");






		/**
		 * Opens the User form when editUsersButton is clicked
		 *
		 * @author Callum McNeilage - n10482652
		 */
		editUsersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User.main(null);
			}
		});


		newBillboard_Button.addActionListener(new ActionListener() {
			/**
			 * Opens New BillboardEditor form when newBillboard_Button is pressed
			 *
			 * @author Connor McHugh - n10522662
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				BillboardEditor.main(null, "New BillboardEditor");
			}
		});

		/*
		newBillboard_Button.addActionListener(new ActionListener() {

			/**
			 * Opens pre-populated billboard form when editButton is pressed
			 *
			 * @author Callum McNeilage - n10482652
			 * @param e the event to be processed
			 /
			@Override
			public void actionPerformed(ActionEvent e) {
				String testXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><billboard background=\"#6800C0\"><message colour=\"#FF9E3F\">All custom colours</message><information colour=\"#3FFFC7\">All custom colours</information><picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" /></billboard>\n";
				BillboardEditor.main(testXML, "Edit BillboardEditor");

				// Query server to get billboard information
				try {
					//Query server
				}
				catch (NullPointerException billErr) {
					System.out.println("No billboard information to display");
				}
			}
		});
		*/
		newEvent_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EventEditor();
			}
		});
	}

	/**
	 * Called by the loginWindow once the user has successfully logged in.
	 * The main window is created here.
	 */
	public void loggedIn() {
		System.out.println("Logged in successfully. Control panel is ready!");

		// Create main window
		setContentPane(mainPanel);
		pack();

		refreshBillboards();
		refreshEvents();
	}

	/**
	 * Takes a date formatted as a long and converts it to Simple Date Format to display in schedule
	 *
	 * @author Callum McNeilage - n10482652
	 * @param unix date formatted as a long
	 * @return Date formatted as h:mm a	d/M/yyyy
	 */
	private String DateFormat(long unix) {
		Date date = new Date(unix);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a \td/M/yyyy");
		return sdf.format(date);
	}


	/**
	 * Call to request the server for the billboards list again
	 */
	public void refreshBillboards() {
		try {
			Response response = ControlPanel.get().requestSender.SendData(EndpointType.listBillboards, null);
			ArrayList<String> billboardNames = (ArrayList<String>)response.getData();

			//Data to be displayed in the JTable
			mainWindowTable.setModel(new DefaultTableModel());
			DefaultTableModel model = (DefaultTableModel) mainWindowTable.getModel();
			model.addColumn("Billboard Name");
			model.addColumn("Author");

			String[] row = new String[2];

			for (String bbName : billboardNames) {
				row[0] = bbName;
				row[1] = "Unknown";

				model.addRow(row);
			}



		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Queries the server for billboard information and displays in table
	 *
	 * @author Callum McNeilage - n10482652
	 */
	public void refreshEvents() {

		//Queries server to return billboard schedule
		ArrayList<Event> list = null;
		try {
			Response response = ControlPanel.get().requestSender.SendData(EndpointType.getEvents, null);

			// Check if the server replied with an error
			if (response.getStatus().equals("error")) {
				if (response.getData().equals("Invalid token")) {
					System.out.println("Your session token is invalid (probably expired), try logging in again");
				}
				else {
					System.out.println("Server replied with an error: " + response.getData().toString());
				}
				return;
			}

			if (response.getData() instanceof ArrayList) {
				list = (ArrayList<Event>) response.getData();
			} else if(response.getData().toString().equals("")){
				System.out.println("No billboards to show");
			}
			else {
				System.out.printf("Server replied with unexpected data: '%s'\n", response.getData().toString());
				return;
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("There was an issue when retrieving billboards");
		}

		//Data to be displayed in the JTable
		schedule_Table.setModel(new DefaultTableModel());
		DefaultTableModel model = (DefaultTableModel) schedule_Table.getModel();
		model.addColumn("Billboard");
		model.addColumn("Start Time");
		model.addColumn("Duration");
		model.addColumn("Repeats");
		model.addColumn("Scheduler");

		Object[] row = new Object[5];
		for (Event event : list) {
			row[0] = event.billboardName;
			row[1] = DateFormat(event.startTime);
			row[2] = event.getDuration() / (60 * 1000);
			row[3] = "No";
			row[4] = event.author;

			model.addRow(row);
		}
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
