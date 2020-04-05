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
		editUsersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame usersFrame = new JFrame("Users");
				usersFrame.setContentPane(new User().Users);
				usersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				usersFrame.pack();
				usersFrame.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("MainWindow");
		mainFrame.setContentPane(new MainWindow().mainWindow);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

}
