package ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewBillboard {
	private JPanel newBillboardWindow;
	private JTextField message;
	private JButton messageColorButton;
	private JButton messageColorDisplay;
	private JButton localFileButton;
	private JButton URLButton;

	public NewBillboard() {
		/**
		 * Form for creating a new billboard
		 *
		 * @author Connor McHugh - n10522662
		 */

		messageColorButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Message Colour' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.black;
				Color messageColor = JColorChooser.showDialog(null, "Choose Message Colour", initialColor); // Brings up the colour chooser dialogue
				messageColorDisplay.setBackground(messageColor); // Sets the inactive button that acts as as 'preview' to the colour selected
			}
		});

		localFileButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Local File' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		URLButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'URL' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Create New Billboard");
		frame.setContentPane(new NewBillboard().newBillboardWindow);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
