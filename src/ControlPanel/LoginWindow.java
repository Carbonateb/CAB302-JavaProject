package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginWindow {
	public JPanel loginWindow;
	private JLabel lblUsername;
	private JButton testButton;
	private JTextField enterUsernameTextField;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JPasswordField enterPasswordPasswordField;
	private JLabel passIncorrect;
	private JLabel placeholderLabel;
	private static JFrame loginFrame;

	public static String encodedPassword;

	/**
	 * Getter to allow actions to be performed on the login window JFrame from outside
	 * of the main function.
	 * @author Connor McHugh - n10522662
	 * @return Returns the JFrame loginFrame
	 */
	public JFrame getLoginFrame() {
		return loginFrame;
	}

	// Handle button click
	public LoginWindow() {

		/**
		 * Graphical User Interface for login form
		 */

		/**
		 * Enables login button if both username and password fields have stuff in them.
		 * This one KeyAdapter is reused for both username and password events
		 * @author Callum McNeilage - n10482652
		 * @contributor Lucas Maldonado - n10534342
		 */
		KeyAdapter loginKeyAdapter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnLogin.setEnabled(
					enterPasswordPasswordField.getPassword().length > 0
						&& enterUsernameTextField.getText().length() > 0
				);
			}
		};

		// Use the key listener from above in both the username and password field
		enterUsernameTextField.addKeyListener(loginKeyAdapter);
		enterPasswordPasswordField.addKeyListener(loginKeyAdapter);

		/**
		 * Called when the user presses enter on the password field
		 * Will click the login button for the user
		 * @author Lucas Maldonado - n10534342
		 */
		enterPasswordPasswordField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
			}
		});

		/**
		 * When pressed checks user credentials and authenticates
		 * @author Callum McNeilage - n10482652
		 * @contributor Connor McHugh - n10522662
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
				System.out.println("\n" + password);

				// Converts password to a SHA-256 encoded password
				try {
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					byte[] encodedhash = md.digest(
						password.getBytes(StandardCharsets.UTF_8));

					encodedPassword = bytesToHex(encodedhash);
					System.out.println(encodedPassword);
				}
				catch (NoSuchAlgorithmException nsae) {
					System.err.println("SHA-256 is not a valid message digest algorithm");
				}





				// Checks if username and password are correct (placeholder for real
				// check that will be implemented later
				if (username.equals("username") && encodedPassword.equals(encodedPassword)) {
					// If the username and password are correct, close the login window
					// and open the main window

					getLoginFrame().dispose();
					JFrame mainFrame = new JFrame("Billboard Control Panel");
					mainFrame.setContentPane(new MainWindow().mainWindow);
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainFrame.pack();
					mainFrame.setVisible(true);
				} else {
					// If the password is incorrect, hide the placeholder label and show
					// the label informing the user that the password is incorrect.
					placeholderLabel.setVisible(false);
					passIncorrect.setVisible(true);
				}
			}
		});
	}

	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++){
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
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
		loginFrame = new JFrame("Billboard Control Panel");
		loginFrame.setContentPane(new LoginWindow().loginWindow);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.pack();
		loginFrame.setVisible(true);
	}

}

/* ========== CODE GRAVEYARD ==========
Unused code goes here:

// controls login button enabled status
	btnLogin.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if (user) {
				btnLogin.setEnabled(true);
				if (password) {

				}
			}
		}
	});

// another better attempt at controlling login button enabled status
	public void keyPressed(KeyEvent e) {
				if(!enterUsernameTextField.getText().trim().isEmpty()) {
					enterPasswordPasswordField.setEnabled(true);
					//user = true;
				}


 */
