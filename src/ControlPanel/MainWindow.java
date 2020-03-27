package ControlPanel;

import javax.swing.*;

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


	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("MainWindow");
		mainFrame.setContentPane(new MainWindow().mainWindow);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

}
