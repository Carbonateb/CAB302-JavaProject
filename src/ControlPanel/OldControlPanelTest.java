package ControlPanel;

import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;

/**
 * ControlPanel remotely controls the Server. This is the main class of the
 * ControlPanel app.
 */
public class OldControlPanelTest {
	public static void main(String[] args) {
		setupUI();
	}

	private static void setupUI() {
		// Won't compile without the exceptions unhandled
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			// handle exception
		}

		// Create and setup main window
		JFrame mainWindow = new JFrame("Billboard Control Panel");
		mainWindow.setSize(400, 250); // Size of the main window
		mainWindow.setLayout(new FlowLayout()); // Arrange widgets centered left to right, top to bottom
		mainWindow.setVisible(true);

		// Create a bit of text
		mainWindow.add(new JLabel("Hello World!"));

		// Create an ugly button
		JButton exitButton = new JButton("Click me");
		mainWindow.add(exitButton); // Add button to window

		// Do stuff when button is clicked
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button pressed");
			}
		});
	}
}
