package ControlPanel;

import Shared.Permissions.Perm;
import Shared.Permissions.Permissions;

import javax.swing.*;
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
	 * @param user See main()
	 * @param perms See main()
	 * @param password See main()
	 */
	public newUser(String user, Permissions perms, Boolean password) {
		textField1.setText(user);

		if (!password | !perms.hasPermission(Perm.EDIT_USERS)) {
			textField2.setEditable(false);
		}

		if (perms.hasPermission(Perm.CREATE_BILLBOARDS)) {
			chkCreate.setSelected(true);
		}
		if (perms.hasPermission(Perm.EDIT_ALL_BILLBOARDS)) {
			chkEdit.setSelected(true);
		}
		if (perms.hasPermission(Perm.SCHEDULE_BILLBOARDS)) {
			chkSchedule.setSelected(true);
		}
		if (perms.hasPermission(Perm.EDIT_USERS)) {
			chkEditUsers.setSelected(true);
		}



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
	 * @param user - Username of selected user - null if newUser
	 * @param perms - Permissions from User.java - null if newUser
	 * @param password - Boolean for if current user is selected user and can edit their password
	 */
	public static void main(String user, Permissions perms, Boolean password) {
		// Won't compile without the exceptions unhandled
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			// handle exception
		}

		// Create and setup newUsers window
		newUserFrame = new JFrame("User");
		newUserFrame.setContentPane(new newUser(user, perms, password).newUser);
		newUserFrame.setDefaultCloseOperation(newUserFrame.HIDE_ON_CLOSE);
		newUserFrame.pack();
		newUserFrame.setVisible(true);
	}
}
