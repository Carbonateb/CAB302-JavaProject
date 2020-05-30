package ControlPanel;

import Server.Endpoints.EndpointType;
import Shared.Credentials;
import Shared.Network.Response;
import Shared.Permissions.Perm;
import Shared.Permissions.Permissions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class newUser {
	public JPanel newUser;
	private JTextField textField1;
	private JTextField textField2;
	private JCheckBox chkCreate;
	private JCheckBox chkEdit;
	private JCheckBox chkSchedule;
	private JCheckBox chkEditUsers;
	private JButton btnSet;
	private JLabel lblPassword;
	public static JFrame newUserFrame;

	/**
	 * Saves all user variables to database and closes window when OK is pressed
	 * @author Callum McNeilage - n10482652
	 * @param user See main()
	 * @param perms See main()
	 */
	public newUser(String user, Permissions perms) {
		textField1.setText(user);
		boolean newUser = (user.equals(""));
		if (newUser) {
			lblPassword.setText("Password");
		}
		textField1.setEditable(newUser);

		Response response = null;
		try {
			response = ControlPanel.get().requestSender.SendData(EndpointType.getUserDetails, ControlPanel.get().requestSender.getToken().getUser());
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		Credentials currentUser = (Credentials) response.getData();
		Permissions currentUserPerms = new Permissions(currentUser.getPermissions());
		if (!currentUserPerms.hasPermission(Perm.EDIT_USERS)) {
			chkCreate.setEnabled(false);
			chkEdit.setEnabled(false);
			chkSchedule.setEnabled(false);
			chkEditUsers.setEnabled(false);
		}

		if (ControlPanel.get().requestSender.getToken().getUser().equals(textField1.getText())) {
			chkEditUsers.setEnabled(false);
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
				Boolean create = false;
				Boolean edit = false;
				Boolean schedule = false;
				Boolean editUsers = false;

				if (chkCreate.isSelected()) {
					create = true;
				}
				else if (chkEdit.isSelected()) {
					edit = true;
				}
				else if (chkSchedule.isSelected()) {
					schedule = true;
				}
				else if (chkEditUsers.isSelected()) {
					editUsers = true;
				}
				else {
					// No permissions
				}

				Permissions permissions = new Permissions();
				if (create) {
					permissions.addPermission(Perm.CREATE_BILLBOARDS);
				}
				if (edit) {
					permissions.addPermission(Perm.EDIT_ALL_BILLBOARDS);
				}
				if (schedule) {
					permissions.addPermission(Perm.SCHEDULE_BILLBOARDS);
				}
				if (editUsers) {
					permissions.addPermission(Perm.EDIT_USERS);
				}

				Credentials credentials = new Credentials(username, password, permissions);

				try {
					Response response = ControlPanel.get().requestSender.SendData(EndpointType.addUser, credentials);
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
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
	 */
	public static void main(String user, Permissions perms) {
		// Won't compile without the exceptions unhandled
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			// handle exception
		}
		boolean newUser = (user == null);

		// Create and setup newUsers window
		newUserFrame = new JFrame("User");
		newUserFrame.setContentPane(new newUser(user, perms).newUser);
		newUserFrame.setDefaultCloseOperation(newUserFrame.HIDE_ON_CLOSE);
		newUserFrame.pack();
		newUserFrame.setLocationRelativeTo(ControlPanel.get());
		newUserFrame.setVisible(true);
	}
}
