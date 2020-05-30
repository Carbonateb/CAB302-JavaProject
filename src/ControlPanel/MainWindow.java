package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Graphical User Interface for main window form
 */

public class MainWindow {
	private JButton createButton;
	private JButton editButton;
	private JButton scheduleButton;
	private JButton editUsersButton;
	public JPanel mainWindow;
	private JTable mainWindowTable;



	public MainWindow() {
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

		/**
		 * Opens the Schedule form when Schedule button if clicked
		 *
		 * @author Callum McNeilage
		 */
		scheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Schedule.main(null);
			}
		});
		createButton.addActionListener(new ActionListener() {
			/**
			 * Opens New BillboardEditor form when createButton is pressed
			 *
			 * @author Connor McHugh - n10522662
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				BillboardEditor.main(null, "New BillboardEditor");
			}
		});

		editButton.addActionListener(new ActionListener() {

			/**
			 * Opens pre-populated billboard form when editButton is pressed
			 *
			 * @author Callum McNeilage - n10482652
			 * @param e the event to be processed
			 */
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
	}
}
