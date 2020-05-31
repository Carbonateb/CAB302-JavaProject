package Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import Server.Endpoints.EndpointType;
import Shared.Billboard;
import Shared.ClientPropsReader;
import Shared.Display.IMGHandler;
import Shared.Network.RequestSender;
import Shared.Network.Response;

/**
 * The viewer is the dummy client that displays the billboard.
 *
 * @author Lucas Maldonado N10534342
 */
public class Viewer {
	private JPanel mainPanel;
	private JTextPane message;
	private JTextPane information;
	private JTextPane image;

	public Billboard billboardToView;

	public ClientPropsReader propsReader;
	public RequestSender requestSender;

	/**
	 * How often the Viewer should get an update from the server, in seconds.
	 * Specification says 15 seconds, remember to change it to that before submitting.
	 */
	public static final int updateFrequency = 15;

	public byte[] imageToByte(String imgString)
	{
		return imgString.getBytes();
	}

	public BufferedImage convertToImage(String imgString) throws IOException {
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageToByte(imgString)));
		return img;
	}

	void setImage(String imgString) throws IOException {
		BufferedImage img = convertToImage(imgString);

		ImageIcon icon = new ImageIcon(img);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(200, 300);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/** Constructor */
	public Viewer() throws IOException {
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

		setImage("\"iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUa\\n\" +\n" +
			"\t\t\t\"W1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI\\n\" +\n" +
			"\t\t\t\"AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr\\n\" +\n" +
			"\t\t\t\"//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm\\n\" +\n" +
			"\t\t\t\"CC\"");
	}

	private Font textFormatter(JTextPane textArea, String textAreaText) {
		StyledDocument test = textArea.getStyledDocument();
		SimpleAttributeSet test2 = new SimpleAttributeSet();
		StyleConstants.setAlignment(test2, StyleConstants.ALIGN_CENTER);
		test.setParagraphAttributes(0, test.getLength()-1, test2, false);

		Font labelFont = textArea.getFont();

		int fontSizeToUse;

		if (textArea == information) {
			fontSizeToUse = 40;
		} else {
			int stringWidth = textArea.getFontMetrics(labelFont).stringWidth(textAreaText);
			int componentWidth = textArea.getWidth();

			double widthRatio = (double)componentWidth / (double)stringWidth;

			int newFontSize = (int)(labelFont.getSize() * widthRatio);
			int componentHeight = textArea.getHeight();

			fontSizeToUse = Math.min(newFontSize, componentHeight);
		}

		return new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse);
	}

	private void clearViewer() {
		message.setText(null);
		information.setText(null);
//		image
		mainPanel.setBackground(Color.white);
		message.setBackground(Color.white);
		information.setBackground(Color.white);
		image.setBackground(Color.white);

	}

	private void populateViewer(String messageText, Color messageColor, String informationText, Color informationColor, Color backgroundColor, String imageString) {

		try {
			if (messageText != null) {
				message.setText(messageText);
				message.setForeground(messageColor);
				message.setFont(textFormatter(message, messageText));
			}

			// Set information text and color
			if (informationText != null) {
				information.setText(informationText);
				information.setForeground(informationColor);
				information.setFont(textFormatter(information, informationText));
			}

			// Set background color
			mainPanel.setBackground(backgroundColor);
			message.setBackground(backgroundColor);
			information.setBackground(backgroundColor);
			image.setBackground(backgroundColor);

			// Set image
			BufferedImage billboardImage = null;
			try {
				billboardImage = IMGHandler.imageDecoder(imageString);
			} catch (NullPointerException | IllegalArgumentException ignored) { }

			try {
				billboardImage = ImageIO.read(new URL(imageString));
			} catch (NullPointerException | IOException ignored) { }



			assert billboardImage != null;
			ImageIcon displayedBillboardImage = new ImageIcon(billboardImage);
			image.insertIcon(displayedBillboardImage);
			StyledDocument sd = image.getStyledDocument();
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
			sd.setParagraphAttributes(0, sd.getLength()-1, sas, false);

		} catch (NullPointerException ex) {
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

		// Set up important classes
		propsReader = new ClientPropsReader();
		requestSender = new RequestSender(
			// Give the requestSender connection info from the .props file
			propsReader.getIPAddress(),
			propsReader.getPort()
		);

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

				//Create variables
				String titleText;
				Color titleTextColor;
				String infoText;
				Color infoTextColor;
				Color backgroundColor;
				String image;

				Response response;
				try {
					response = requestSender.SendData(EndpointType.getCurrentBillboard, null);

					billboardToView = (Billboard) response.getData();

					titleText = billboardToView.titleText;
					titleTextColor = billboardToView.titleTextColor;
					infoText = billboardToView.infoText;
					infoTextColor = billboardToView.infoTextColor;
					backgroundColor = billboardToView.backgroundColor;
					image = billboardToView.image;

				} catch (IOException | ClassNotFoundException e) {
					titleText = "Cannot connect to Server!";
					titleTextColor = Color.red;
					infoText = "Please confirm server is running on network";
					infoTextColor = Color.red;
					backgroundColor = Color.black;
					image = null;
					e.printStackTrace();
				} catch (NullPointerException err) {
					titleText = "No Billboards Scheduled";
					titleTextColor = Color.red;
					infoText = "Please add billboards to the schedule in the Billboard Controller";
					infoTextColor = Color.red;
					backgroundColor = Color.black;
					image = null;
				}

				populateViewer(titleText, titleTextColor, infoText, infoTextColor, backgroundColor, image);

			}

		}, 0, updateFrequency * 1000);
	}


	/**
	 * Entry point of the viewer, just creates an instance of itself.
	 * @param args Unused
	 */
	public static void main(String[] args) throws IOException {
		new Viewer();
	}
}
