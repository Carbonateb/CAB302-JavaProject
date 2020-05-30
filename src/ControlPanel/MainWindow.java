package ControlPanel;

import Server.Endpoints.EndpointType;
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

public class MainWindow {
	private JButton newBillboard_Button;
	private JButton editUsersButton;
	public JPanel mainWindow;
	private JTable schedule_Table;
	private JButton newEvent_Button;
	private JTable mainWindowTable;
	private JTextPane billboardControlPanelV0TextPane;


	public MainWindow() {

		refershBillboards();
		refreshEvents();

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
	 * Takes a date formatted as a long and converts it to Simple Date Format to display in schedule
	 *
	 * @author Callum McNeilage - n10482652
	 * @param unix date formatted as a long
	 * @return Date formatted as yyyy-MM-dd HH:mm:ss z
	 */
	private String DateFormat(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		return sdf.format(date);
	}


	public void refershBillboards() {
		try {
			Response response = ControlPanel.get().requestSender.SendData(EndpointType.listBillboards, null);
			ArrayList<String> billboardNames = (ArrayList<String>)response.getData();

			//Data to be displayed in the JTable
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
			row[2] = event.getDuration();
			row[3] = "No";
			row[4] = event.author;

			model.addRow(row);
		}
	}
}
