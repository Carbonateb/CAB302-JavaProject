package Viewer;

import Server.Endpoints.EndpointType;
import Shared.Billboard;
import Shared.ClientPropsReader;
import Shared.Network.RequestSender;
import Shared.Network.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * The viewer class just creates a BillboardDisplay window, full-screens it and
 * gets updates from the server every 15 seconds.
 *
 * It's the the dummy client that displays the billboard.
 *
 * @author Lucas Maldonado n10534342
 */
public class Viewer extends JFrame {

	public BillboardDisplay billboardDisplay;

	public ClientPropsReader propsReader;
	public RequestSender requestSender;

	/**
	 * How often the Viewer should get an update from the server, in seconds.
	 * Specification says 15 seconds, remember to change it to that before submitting.
	 */
	public static final int updateFrequency = 15;

	/**
	 * Called by Main()
	 */
	private Viewer() {
		System.out.println("Viewer starting...");
		propsReader = new ClientPropsReader();
		requestSender = new RequestSender(propsReader.getIPAddress(), propsReader.getPort());

		CreateUI();
		CreateUpdateTimer();
		System.out.println("Viewer started successfully");

		information.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, -1));
		message.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, -1));

		StyledDocument doc = message.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		doc = information.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

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

	private void clearViewer() {
		message.setText(null);
		information.setText(null);
		mainPanel.setBackground(null);
		message.setBackground(null);
		information.setBackground(null);
		image.setText(null);

	}

	private ImageIcon imageScaler(Image billboardImage, int imageWidth, int imageHeight, int desiredHeight) {
		double scaleFactor = (double) (imageHeight / desiredHeight);
		int newWidth = (int) Math.round(imageWidth / scaleFactor);

		return new ImageIcon(new ImageIcon(billboardImage).getImage().getScaledInstance(newWidth, desiredHeight, Image.SCALE_DEFAULT));
	}


	private void populateViewer(String messageText, Color messageColor, String informationText, Color informationColor, Color backgroundColor, String imageString) {

		clearViewer();

		if (messageText == null) {
			messageText = "";
		} if (informationText == null) {
			informationText = "";
		} if (imageString == null) {
			imageString = "";
		}

//		System.out.println("IMAGE STRING:::" + imageString);

		// Check which components need to be enabled
		if (!messageText.equals("")) {
			message.setVisible(true);
		} if (!informationText.equals("")) {
			information.setVisible(true);
		} if (!imageString.equals("")) {
			image.setVisible(true);
		}

		try {
			if (!messageText.equals("")) {
				message.setText(messageText);
				message.setForeground(messageColor);
			}

			// Set information text and color
			if (!informationText.equals("")) {
				information.setText(informationText);
				information.setForeground(informationColor);
			}

			// Set background color
			mainPanel.setBackground(backgroundColor);
			message.setBackground(backgroundColor);
			information.setBackground(backgroundColor);
			image.setBackground(backgroundColor);

			System.out.println(imageString);

			// Set image
			BufferedImage billboardImage = null;
			try {
				billboardImage = IMGHandler.imageDecoder(imageString);
			} catch (NullPointerException | IllegalArgumentException e) {
				e.printStackTrace();
			}

		System.out.println("Viewer started successfully");
	}


	/**
	 * Creates the main JFrame and sets it up.
	 * This is where we full screen the window.
	 */
	private void CreateUI(){

		billboardDisplay = new BillboardDisplay();

		setTitle("Billboard Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setContentPane(billboardDisplay.mainPanel);
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);

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
		billboardDisplay.mainPanel.grabFocus();
		billboardDisplay.mainPanel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "exitProgram");
		billboardDisplay.mainPanel.addMouseListener(new MouseListener() {
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
		billboardDisplay.mainPanel.getActionMap().put("exitProgram", exitProgram);
		// Do this last
		setVisible(true);
	}


	/**
	 * Create the timer that will get an update to the server every 15 secs
	 */
	private void CreateUpdateTimer(){
		java.util.Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			/** Contact the server for info here */
			@Override
			public void run() {
				System.out.println("Requesting info from server...");

				Response response;
				try {
					response = requestSender.SendData(EndpointType.getCurrentBillboard, null);

					billboardDisplay.setBillboard((Billboard) response.getData());

				} catch (IOException | ClassNotFoundException e) {
					billboardDisplay.setBillboard(new Billboard(
						"Cannot connect to Server!",
						"Cannot connect to Server!",
						"Please confirm server is running on network",
						Color.red,
						Color.red,
						Color.darkGray,
						"",
						"No Author"
					));
				}
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
