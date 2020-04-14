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
}
