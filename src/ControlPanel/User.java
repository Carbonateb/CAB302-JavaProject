package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User {
	public JPanel Users;
	private JButton btnNewUser;
	private JList lstNames;
	private JButton btnSelect;

	public User() {
		/**
		 * Opens a form to create new users
		 *
		 * @author Callum McNeilage - n10482652
		 */
		btnNewUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newUser.main(null);
			}
		});
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newUser.main(null);
			}
		});
	}

	/**
	 * Loads User list window
	 *
	 * @author Callum McNeilage - n10482652
	 * @param args
	 */
	public static void main(String[] args) {
		// Won't compile without the exceptions unhandled
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			// handle exception
		}

		// Create and setup Users window
		JFrame usersFrame = new JFrame("Users");
		usersFrame.setContentPane(new User().Users);
		usersFrame.setDefaultCloseOperation(usersFrame.HIDE_ON_CLOSE);
		usersFrame.pack();
		usersFrame.setVisible(true);
	}
}
