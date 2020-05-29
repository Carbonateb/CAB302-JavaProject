package ControlPanel;
import Server.Endpoints.EndpointType;
import Shared.Credentials;
import Shared.Network.RequestSender;
import Shared.Network.Response;
import Shared.Schedule.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class times {
	private JPanel timesWindow;
	private JButton btnCancel;
	private JButton btnOk;
	private JSpinner spinner;
	private JComboBox comboBox1;
	private JCheckBox enableLoopingCheckBox;
	private JTextField a05062001TextField;
	private JTextField a1223AmTextField;
	public static JFrame timesFrame;

	/**
	 * Allows the user to schedule a billboard. Has controls for setting a start date & time, duration, and looping
	 * behaviours. All user input is sanitized. The inputs are converted to unix time for convenience.
	 *
	 * When the user presses OK, a new Event is created, which is handed off to WIP
	 *
	 * @author Callum McNeilage - n10482652
	 * @contributor Lucas Maldonado - n10534342
	 */
	public times() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Send data to server
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				//Retrieve start string in UTC format
				String startTime = txtStart.getText();
				System.out.println(startTime);

				//Retrieve end time in minutes int
				int endTime = (int) spinner.getValue();
				endTime = endTime * 60 * 1000;
				System.out.println(endTime);

				//Retrieve billboard ID
				String billboardName = txtID.getText();
				System.out.println(billboardName);

				try {
					//Convert UTC strings to Date objects
					Date start = sdf.parse(startTime);

					//Convert Date objects to Unix time for server
					long startTimeStamp = start.getTime();
					long endTimestamp = startTimeStamp + endTime;

					System.out.println(startTimeStamp);
					System.out.println(endTimestamp);

					//Send data to server
					RequestSender requestSender = new RequestSender("localhost", 9977);
					Credentials credentials = new Credentials("username1234", "password1234", null);
					requestSender.login(credentials);


					String name = billboardName;
					Event eventObj = new Event(startTimeStamp, endTimestamp, name, credentials.getUsername());
					Response request = requestSender.SendData(EndpointType.addEvents, eventObj);
					System.out.println(request);

				}
				catch (ParseException err) {
					System.out.println("DateTime in wrong format please use yyyy-MM-dd'T'HH:mm:ss format");
				}
				catch (NumberFormatException numerr) {
					System.out.println("Please use only whole numbers in Billboard ID");
				}
				catch (IOException | ClassNotFoundException e1) {
					System.out.println("Error in POST");
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
}
