package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControlPanel {
	public JPanel mainWindow;
	private JLabel lblUsername;
	private JButton testButton;
	private JTextField enterUsernameTextField;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JPasswordField enterPasswordPasswordField;

	//Variables
	public boolean user;
	public boolean password;

	// Handle button click
	public ControlPanel() {

		/**
		 * Graphical User Interface for login form
		 */

		/**
		 * Checks for when user inputs username and enables the password field
		 *  @author Callum McNeilage - n10482652
		 */
		enterUsernameTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!enterUsernameTextField.getText().trim().isEmpty()) {
					enterPasswordPasswordField.setEnabled(true);
					//user = true;
				}
			}
		});

		/**
		 * Checks for user to input password and enables login button
		 *  @author Callum McNeilage - n10482652
		 */
		enterPasswordPasswordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!enterPasswordPasswordField.getPassword().equals("")) {
					btnLogin.setEnabled(true);
					//password = true;
				}
			}
		});
		/*btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (user) {
					btnLogin.setEnabled(true);
					if (password) {

					}
				}
			}
		});*/

		/**
		 * When pressed checks user credentials and authenticates
		 *  @author Callum McNeilage - n10482652
		 */
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button pressed");

				String username = enterUsernameTextField.getText();
				char[] pwd = enterPasswordPasswordField.getPassword();
				String password = "";

				System.out.println(username);
				for (int i = 0; i < pwd.length; i++) {
					System.out.print(pwd[i]);
					password += pwd[i];
				}
				System.out.println(password.hashCode());
			}
		});
	}

	public static void main(String[] args) {
		// Won't compile without the exceptions unhandled
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			// handle exception
		}

		// Create and setup main window
		JFrame frame = new JFrame("Billboard Control Panel");
		frame.setContentPane(new ControlPanel().mainWindow);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
