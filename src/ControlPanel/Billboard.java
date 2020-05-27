package ControlPanel;

import Shared.Display.IMGHandler;
import Shared.Display.XMLHandler;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Form for creating a new billboard
 *
 * @author Connor McHugh - n10522662
 * @contributor Callum McNeilage - n10482652
 */
public class Billboard {
	public JPanel BillboardWindow;
	private JTextField message;
	private JButton messageColorButton;
	private JButton localFileButton;
	private JButton URLButton;
	private JLabel selectedFileLabel;
	private JTextField information;
	private JButton informationColorButton;
	private JButton backgroundColorButton;
	private JButton exportXMLButton;
	private JButton importXMLButton;
	private JTextField messageColorPreview;
	private JTextField informationColorPreview;
	private JTextField backgroundColorPreview;
	private JButton okayButton;
	private JButton cancelButton;
	public static JFrame billboardFrame;

	private void SetValues(boolean isFile, String xmlData) {
		try {
			// Set message text and color
			Color messageColor;
			String messageText = XMLHandler.xmlReader(isFile, xmlData, "message", null);
			if (messageText != null) {
				message.setText(messageText);
				try {
					messageColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(isFile, xmlData, "message", "colour")));
				} catch (NullPointerException r) {
					messageColor = Color.black;
				}
				messageColorPreview.setBackground(messageColor);
			}

			// Set information text and color
			Color informationColor;
			String informationText = XMLHandler.xmlReader(isFile, xmlData, "information", null);
			if (informationText != null) {
				information.setText(informationText);
				try {
					informationColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(isFile, xmlData, "information", "colour")));
				} catch (NullPointerException r) {
					informationColor = Color.black;
				}
				informationColorPreview.setBackground(informationColor);

			}

			// Set background color
			Color backgroundColor;
			try {
				backgroundColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(isFile, xmlData, "billboard", "background")));
			} catch (NullPointerException r) {
				backgroundColor = Color.white;
			}
			backgroundColorPreview.setBackground(backgroundColor);

			// Set image
			String image = null;
			try {
				image = XMLHandler.xmlReader(isFile, xmlData, "picture", "data");
			} catch (NullPointerException ignored) {
			}
			try {
				image = XMLHandler.xmlReader(isFile, xmlData, "picture", "url");
			} catch (NullPointerException ignored) {
			}

			if (image != null) {
				System.out.println(image);
				selectedFileLabel.setText(image);
			}
		} catch (ParserConfigurationException | IOException | SAXException | NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	public Billboard(String xmlString) {
		if (xmlString != null) {
			System.out.println(xmlString);
			SetValues(false, xmlString);
		}

		messageColorButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Message Color' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.black;
				Color messageColor = JColorChooser.showDialog(null, "Choose Message Color", initialColor); // Brings up the color chooser dialogue
				messageColorPreview.setBackground(messageColor); // Sets the inactive button that acts as as 'preview' to the color selected
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
				String localImagePath = null;

				JFileChooser imageSelect = new JFileChooser();
				imageSelect.setAcceptAllFileFilterUsed(false);
				imageSelect.setDialogTitle("Select an image");

				FileNameExtensionFilter restrict = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "bmp", "png");
				imageSelect.addChoosableFileFilter(restrict);

				int selected = imageSelect.showOpenDialog(null);

				if (selected == JFileChooser.APPROVE_OPTION) {
					localImagePath = imageSelect.getSelectedFile().getAbsolutePath();
					selectedFileLabel.setText(localImagePath);
				}

				BufferedImage image = null;

				try
				{
					assert localImagePath != null;
					image = ImageIO.read(new File(localImagePath));
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}

				String fileExtension = "";

				int i = localImagePath.lastIndexOf('.');
				int p = Math.max(localImagePath.lastIndexOf('/'), localImagePath.lastIndexOf('\\'));

				if (i > p) {
					fileExtension = localImagePath.substring(i+1);
				}

				String imageBase64String = IMGHandler.imageEncoder(image, fileExtension);
				System.out.println(imageBase64String);
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

		informationColorButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Information Text Color' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.black;
				Color informationColor = JColorChooser.showDialog(null, "Choose Information Text Color", initialColor); // Brings up the color chooser dialogue
				informationColorPreview.setBackground(informationColor); // Sets the inactive button that acts as as 'preview' to the color selected
			}
		});

		backgroundColorButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Background Color' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.white;
				Color backgroundColor = JColorChooser.showDialog(null, "Choose Background Color", initialColor); // Brings up the color chooser dialogue
				backgroundColorPreview.setBackground(backgroundColor); // Sets the inactive button that acts as as 'preview' to the color selected
			}
		});

		importXMLButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Import XML' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open file chooser dialogue to select XML file to load
				JFileChooser xmlSelect = new JFileChooser();
				xmlSelect.setAcceptAllFileFilterUsed(false);
				xmlSelect.setDialogTitle("Select an XML file");

				FileNameExtensionFilter restrict = new FileNameExtensionFilter("XML files", "xml");
				xmlSelect.addChoosableFileFilter(restrict);

				String xmlImportPath = null;
				int selected = xmlSelect.showOpenDialog(null);

				if (selected == JFileChooser.APPROVE_OPTION) {
					xmlImportPath = xmlSelect.getSelectedFile().getAbsolutePath();
					// Clear any current values
					message.setText(null);
					messageColorPreview.setBackground(Color.black);
					information.setText(null);
					informationColorPreview.setBackground(Color.black);
					backgroundColorPreview.setBackground(Color.white);
				}

				SetValues(true, xmlImportPath);
			}
		});

		exportXMLButton.addActionListener(new ActionListener() {
			/**
			 * Handle the 'Export XML' button being pressed
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open file chooser dialogue to select XML file to load
				JFileChooser xmlSave = new JFileChooser();
				xmlSave.setAcceptAllFileFilterUsed(false);
				xmlSave.setDialogTitle("Save To");

				FileNameExtensionFilter restrict = new FileNameExtensionFilter("XML files", "xml");
				xmlSave.addChoosableFileFilter(restrict);

				String xmlExportPath = null;
				int selected = xmlSave.showSaveDialog(null);

				if (selected == JFileChooser.APPROVE_OPTION) {
					xmlExportPath = xmlSave.getSelectedFile().getAbsolutePath();

					String messageText = message.getText();
					Color messageColor = messageColorPreview.getBackground();
					String informationText = information.getText();
					Color informationColor = informationColorPreview.getBackground();
					Color backgroundColor = backgroundColorPreview.getBackground();
					String image = selectedFileLabel.getText();

					try {
						XMLHandler.xmlWriter(xmlExportPath, messageText, messageColor, informationText, informationColor, backgroundColor, image);
					} catch (ParserConfigurationException parserConfigurationException) {
						parserConfigurationException.printStackTrace();
					}
				}
			}
		});

		okayButton.addActionListener(new ActionListener() {
			/**
			 * Saves data and closes window
			 *
			 * @author Callum McNeilage - n10482652
			 * @param e
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				billboardFrame.dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			/**
			 * Closes window without saving data
			 *
			 * @author Callum McNeilage - n10482652
			 * @param e
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				billboardFrame.dispose();
			}
		});
	}

	public static void main(String xmlString, String title) {

		billboardFrame = new JFrame(title);
		if (xmlString != null) {
			billboardFrame.setContentPane(new Billboard(xmlString).BillboardWindow);
		} else {
			billboardFrame.setContentPane(new Billboard(null).BillboardWindow);
		}

		billboardFrame.setDefaultCloseOperation(billboardFrame.HIDE_ON_CLOSE);
		billboardFrame.pack();
		billboardFrame.setVisible(true);
		billboardFrame.setResizable(false);
	}

}
