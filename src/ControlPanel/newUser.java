package ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newUser {
	public JPanel newUser;
	private JTextField textField1;
	private JTextField textField2;
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JCheckBox checkBox3;
	private JCheckBox checkBox4;
	private JButton btnSet;
	private JCheckBox useOneTimePasswordCheckBox;
	private JCheckBox checkBox6;
	private JCheckBox checkBox5;
	public static JFrame newUserFrame;

	public newUser() {
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newUserFrame.dispose();
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
		newUserFrame = new JFrame("New User");
		newUserFrame.setContentPane(new newUser().newUser);
		newUserFrame.setDefaultCloseOperation(newUserFrame.HIDE_ON_CLOSE);
		newUserFrame.pack();
		newUserFrame.setVisible(true);
	}
}
