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
			 * Opens New Billboard form when createButton is pressed
			 *
			 * @author Connor McHugh - n10522662
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame newBillboardFrame = new JFrame("Create New Billboard");
				newBillboardFrame.setContentPane(new Billboard().BillboardWindow);
				newBillboardFrame.setDefaultCloseOperation(newBillboardFrame.HIDE_ON_CLOSE);
				newBillboardFrame.pack();
				newBillboardFrame.setVisible(true);
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
				JFrame billboardFrame = new JFrame("Edit Billboard");
				billboardFrame.setContentPane((new Billboard().BillboardWindow));
				billboardFrame.setDefaultCloseOperation(billboardFrame.HIDE_ON_CLOSE);
				billboardFrame.pack();
				billboardFrame.setVisible(true);
			}
		});
	}

	/**
	 * Loads the main application window
	 *
	 * @author Connor McHugh -
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("MainWindow");
		mainFrame.setContentPane(new MainWindow().mainWindow);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

}
