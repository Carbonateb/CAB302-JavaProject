package ControlPanel;

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
import java.util.stream.Stream;

/**
 * Form for creating a new billboard
 *
 * @author Connor McHugh - n10522662
 */
public class NewBillboard {
	public JPanel newBillboardWindow;
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


	public NewBillboard() {
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
				try {
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
					}

					// Set message text and color
					message.setText(XMLHandler.xmlReader(xmlImportPath, "message", "null"));
					Color messageColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(xmlImportPath, "message", "colour")));
					messageColorPreview.setBackground(messageColor);

					// Set information text and color
					information.setText(XMLHandler.xmlReader(xmlImportPath, "information", "null"));
					Color informationColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(xmlImportPath, "information", "colour")));
					informationColorPreview.setBackground(informationColor);

					// Set background color
					Color backgroundColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(xmlImportPath, "billboard", "background")));
					backgroundColorPreview.setBackground(backgroundColor);

				} catch (ParserConfigurationException | IOException | SAXException ex) {
					ex.printStackTrace();
				}
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

			}
		});
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Create New Billboard");
		frame.setContentPane(new NewBillboard().newBillboardWindow);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
//		frame.setResizable(false);
	}

}
