package Viewer;

import Shared.Display.IMGHandler;
import Shared.Display.XMLHandler;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The viewer is the dummy client that displays the billboard.
 *
 * @author Lucas Maldonado N10534342
 */
public class Viewer {
	private JPanel mainPanel;
	private JTextPane testTextPane; // temporary
	private JTextPane message;
	private JTextPane information;
	private JTextPane image;

	/**
	 * How often the Viewer should get an update from the server, in seconds.
	 * Specification says 15 seconds, remember to change it to that before submitting.
	 */
	public static final int updateFrequency = 15;


	/** Constructor */
	public Viewer()
	{
		System.out.println("Viewer starting...");
		CreateUI();
		CreateUpdateTimer();
		System.out.println("Viewer started successfully");

		message.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 *
			 * @param e
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1)
					System.exit(0);
			}
		});
		image.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 *
			 * @param e
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1)
					System.exit(0);
			}
		});
		information.addMouseListener(new MouseAdapter() {
			/**
			 * {@inheritDoc}
			 *
			 * @param e
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1)
					System.exit(0);
			}
		});
	}


	private void populateViewer() {
//		String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><billboard background=\"#6800C0\"><message colour=\"#FF9E3F\">All custom colours</message><information colour=\"#3FFFC7\">All custom colours</information><picture url=\"https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download\" /></billboard>\n";
		String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><billboard background=\"#6800C0\"><message colour=\"#FF9E3F\">All custom colours</message><information colour=\"#3FFFC7\">All custom colours</information><picture data=\"iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAApElEQVR42u3RAQ0AAAjDMO5fNCCDkC5z0HTVrisFCBABASIgQAQEiIAAAQJEQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAREQIAICBABASIgQAQECBAgAgJEQIAIyPcGFY7HnV2aPXoAAAAASUVORK5CYII=\" />\n</billboard>\n";


		try {
			// Set message text and color
			Color messageColor;
			String messageText = XMLHandler.xmlReader(false, xmlData, "message", null);
			if (messageText != null) {
				message.setText(messageText);
				try {
					messageColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(false, xmlData, "message", "colour")));
				} catch (NullPointerException r) {
					messageColor = Color.black;
				}
				message.setForeground(messageColor);
				StyledDocument test = message.getStyledDocument();
				SimpleAttributeSet test2 = new SimpleAttributeSet();
				StyleConstants.setAlignment(test2, StyleConstants.ALIGN_CENTER);
				test.setParagraphAttributes(0, test.getLength()-1, test2, false);
			}

			// Set information text and color
			Color informationColor;
			String informationText = XMLHandler.xmlReader(false, xmlData, "information", null);
			if (informationText != null) {
				information.setText(informationText);
				try {
					informationColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(false, xmlData, "information", "colour")));
				} catch (NullPointerException r) {
					informationColor = Color.black;
				}
				information.setForeground(informationColor);
				StyledDocument test = information.getStyledDocument();
				SimpleAttributeSet test2 = new SimpleAttributeSet();
				StyleConstants.setAlignment(test2, StyleConstants.ALIGN_CENTER);
				test.setParagraphAttributes(0, test.getLength()-1, test2, false);
			}

			// Set background color
			Color backgroundColor;
			try {
				backgroundColor = Color.decode(Objects.requireNonNull(XMLHandler.xmlReader(false, xmlData, "billboard", "background")));
			} catch (NullPointerException r) {
				backgroundColor = Color.white;
			}
			mainPanel.setBackground(backgroundColor);
			message.setBackground(backgroundColor);
			information.setBackground(backgroundColor);
			image.setBackground(backgroundColor);

			// Set image
			String imageString;
			BufferedImage billboardImage = null;
			try {
				imageString = XMLHandler.xmlReader(false, xmlData, "picture", "data");
				billboardImage = IMGHandler.imageDecoder(imageString);
			} catch (NullPointerException ignored) { }

			try {
				imageString = XMLHandler.xmlReader(false, xmlData, "picture", "url");
				assert imageString != null;
				billboardImage = ImageIO.read(new URL(imageString));
			} catch (NullPointerException | MalformedURLException ignored) { }

			assert billboardImage != null;
			ImageIcon displayedBillboardImage = new ImageIcon(billboardImage);
			image.insertIcon(displayedBillboardImage);
			StyledDocument test = image.getStyledDocument();
			SimpleAttributeSet test2 = new SimpleAttributeSet();
			StyleConstants.setAlignment(test2, StyleConstants.ALIGN_CENTER);
			test.setParagraphAttributes(0, test.getLength()-1, test2, false);

		} catch (ParserConfigurationException | IOException | SAXException | NullPointerException ex) {
			ex.printStackTrace();
		}
	}


	/**
	 * Creates the main JFrame and sets it up.
	 * This is where we full screen the window.
	 */
	private void CreateUI(){
		JFrame mainFrame = new JFrame("Billboard Viewer");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setUndecorated(true);
		mainFrame.setContentPane(mainPanel);
		mainFrame.pack();
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);

		Action exitProgram = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

		// Setup input binds, ESC and clicking should quit program
		mainPanel.grabFocus();
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "exitProgram");
		mainPanel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 1 = LMB
				// 2 = ScrollWheel
				// 3 = RMB
				// 4 = SideButtonBack
				// 5 = SideButtonFront
				if (e.getButton() == 1)
					System.exit(0);
			}
			// These need to be here
			@Override public void mousePressed(MouseEvent e) { }
			@Override public void mouseReleased(MouseEvent e) { }
			@Override public void mouseEntered(MouseEvent e) { }
			@Override public void mouseExited(MouseEvent e) { }
		});
		mainPanel.getActionMap().put("exitProgram", exitProgram);

		populateViewer();
	}


	/**
	 * Create the timer that will get an update to the server every 15 secs
	 */
	private void CreateUpdateTimer(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			/** Contact the server for info here */
			@Override
			public void run() {
				System.out.println("Requesting info from server...");
				// TODO make viewer request info from server
			}

		}, 0, updateFrequency * 1000);
	}


	/**
	 * Entry point of the viewer, just creates an instance of itself.
	 * @param args Unused
	 */
	public static void main(String args[]) {
		new Viewer();
	}
}
