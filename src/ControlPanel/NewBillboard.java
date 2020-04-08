package ControlPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewBillboard {
	public JPanel newBillboardWindow;
	private JTextField message;
	private JButton messageColorButton;
	private JButton localFileButton;
	private JButton URLButton;
	private JLabel selectedFileLabel;
	private JTextArea information;
	private JButton informationTextColourButton;
	private JButton backgroundColourButton;
	private JButton exportXMLButton;
	private JButton importXMLButton;
	private JTextField messageColorPreview;
	private JTextField informationColorPreview;
	private JTextField backgroundColorPreview;
	private JButton okayButton;
	private JButton cancelButton;

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
				messageColorPreview.setBackground(messageColor); // Sets the inactive button that acts as as 'preview' to the colour selected
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
				JFileChooser imageSelect = new JFileChooser();
				imageSelect.setAcceptAllFileFilterUsed(false);
				imageSelect.setDialogTitle("Select and image");

				FileNameExtensionFilter restrict = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "bmp", "png");
				imageSelect.addChoosableFileFilter(restrict);

				int selected = imageSelect.showOpenDialog(null);

				if (selected == JFileChooser.APPROVE_OPTION) {
					String localImagePath = imageSelect.getSelectedFile().getAbsolutePath();
					selectedFileLabel.setText(localImagePath);
				}
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
				JFrame frame = new JFrame();
				// Open dialogue box for the user to enter a URL
				String imageURL = (String)JOptionPane.showInputDialog(
					frame,
					"Enter the URL of the image that you would like to use",
					"Enter Image URL",
					JOptionPane.PLAIN_MESSAGE
				);

				// As long as the user has entered a URL, update the preview label
				if ((imageURL != null) && (imageURL.length() > 0)) {
					selectedFileLabel.setText(imageURL);
				}
			}
		});

		informationTextColourButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Information Text Colour' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.black;
				Color informationTextColour = JColorChooser.showDialog(null, "Choose Information Text Colour", initialColor); // Brings up the colour chooser dialogue
				informationColorPreview.setBackground(informationTextColour); // Sets the inactive button that acts as as 'preview' to the colour selected
			}
		});

		backgroundColourButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Background Colour' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.white;
				Color informationTextColour = JColorChooser.showDialog(null, "Choose Background Colour", initialColor); // Brings up the colour chooser dialogue
				backgroundColorPreview.setBackground(informationTextColour); // Sets the inactive button that acts as as 'preview' to the colour selected
			}
		});
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Create New Billboard");
		frame.setContentPane(new NewBillboard().newBillboardWindow);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
//		frame.setResizable(false);
	}

}
