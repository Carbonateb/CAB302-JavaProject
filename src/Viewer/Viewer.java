package Viewer;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The viewer is the dummy client that displays the billboard.
 *
 * @author Lucas Maldonado N10534342
 */
public class Viewer {
	private JPanel mainPanel;

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
