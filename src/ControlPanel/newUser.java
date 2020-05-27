package ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newUser {
	public JPanel newUser;
	private JTextField textField1;
	private JTextField textField2;
	private JCheckBox chkCreate;
	private JCheckBox chkEdit;
	private JCheckBox chkSchedule;
	private JCheckBox chkEditUsers;
	private JButton btnSet;
	public static JFrame newUserFrame;

	/**
	 * Saves all user variables to database and closes window when OK is pressed
	 * @author Callum McNeilage - n10482652
	 */
	public newUser() {
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textField1.getText();
				String password = textField2.getText();

				if (chkCreate.isSelected()) {
					Boolean create = true;
				}
				else if (chkEdit.isSelected()) {
					Boolean edit = true;
				}
				else if (chkSchedule.isSelected()) {
					Boolean schedule = true;
				}
				else if (chkEditUsers.isSelected()) {
					Boolean editUsers = true;
				}
				else {
					// No permissions
				}

				newUserFrame.dispose();
			}
		});
	}

	/**
	 * Loads New User/Edit User window
	 * - The new User window will load blank while edit user window will load with user credentials filled out
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

		// Create and setup newUsers window
		newUserFrame = new JFrame("New User");
		newUserFrame.setContentPane(new newUser().newUser);
		newUserFrame.setDefaultCloseOperation(newUserFrame.HIDE_ON_CLOSE);
		newUserFrame.pack();
		newUserFrame.setVisible(true);
	}
}
