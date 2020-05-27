package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class times {
	private JPanel timesWindow;
	private JTextField txtStart;
	private JButton btnCancel;
	private JButton btnOk;
	private JSpinner spinner;
	public static JFrame timesFrame;

	/**
	 * Takes two DateTime strings written in UTC time and converts them to Date objects and Unix time
	 *
	 * @author Callum McNeilage - n10482652
	 */
	public times() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Send data to server
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				//Retrieve start string in UTC format
				String startTime = txtStart.getText();

				//Retrieve end time in minutes int
				int endTime = (int) spinner.getValue();
				endTime = endTime * 60 * 1000;

				try {
					//Convert UTC strings to Date objects
					Date start = sdf.parse(startTime);

					//Convert Date objects to Unix time for server
					long startTimeStamp = start.getTime();
					long endTimestamp = startTimeStamp + endTime;

					System.out.println(startTimeStamp);
					System.out.println(endTimestamp);

				} catch (ParseException err) {
					System.out.println("DateTime in wrong format please use yyyy-MM-dd'T'HH:mm:ss format");
				}

				timesFrame.dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timesFrame.dispose();
			}
		});
	}

	/**
	 * Loads the schedule times window
	 *
	 * @author Callum McNeilage - n10482652
	 * @param args
	 */
	public static void main(String[] args) {
		timesFrame = new JFrame("Schedule Times");
		timesFrame.setContentPane(new times().timesWindow);
		timesFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		timesFrame.pack();
		timesFrame.setVisible(true);
	}
}
