package ControlPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventCreator extends JFrame {
	private JPanel main_Panel;
	private JButton cancel_Button;
	private JButton ok_Button;
	private JSpinner duration_Spinner;
	private JComboBox billboardSelector_ComboBox;
	private JCheckBox enableLooping_ChkBox;
	private JTextField startDate_TextField;
	private JTextField startTime_TextField;
	private JSpinner loopWeeks_Spinner;
	private JSpinner loopDays_Spinner;
	private JSpinner loopHours_spinner;
	private JSpinner loopMinutes_Spinner;

	/**
	 * Allows the user to schedule a billboard. Has controls for setting a start date & time, duration, and looping
	 * behaviours. All user input is sanitized. The inputs are converted to unix time for convenience.
	 *
	 * When the user presses OK, a new Event is created, which is handed off to WIP
	 *
	 * @author Callum McNeilage - n10482652
	 * @contributor Lucas Maldonado - n10534342
	 */
	public EventCreator() {
		// Set up frame
		setTitle("Schedule Billboard");
		setContentPane(main_Panel);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setVisible(true);

		startTime_TextField.setText("Current time");
		startDate_TextField.setText("Current date");

		ok_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {



			}
		});
		cancel_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}

/*

				//Send data to server
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				//Retrieve start string in UTC format
				String startTime_TextField = txtStart.getText();
				System.out.println(startTime_TextField);

				//Retrieve end time in minutes int
				int endTime = (int) duration_Spinner.getValue();
				endTime = endTime * 60 * 1000;
				System.out.println(endTime);

				//Retrieve billboard ID
				String billboardName = txtID.getText();
				System.out.println(billboardName);

				try {
					//Convert UTC strings to Date objects
					Date start = sdf.parse(startTime_TextField);

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

 */
