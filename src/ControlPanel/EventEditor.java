package ControlPanel;

import Server.Endpoints.EndpointType;
import Shared.Schedule.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EventEditor extends JFrame {
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

	private SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH);

	/**
	 * Allows the user to schedule a billboard. Has controls for setting a start date & time, duration, and looping
	 * behaviours. All user input is sanitized. The inputs are converted to unix time for convenience.
	 *
	 * When the user presses OK, a new Event is created, which is handed off to WIP
	 *
	 * @author Callum McNeilage - n10482652
	 * @contributor Lucas Maldonado - n10534342
	 */
	public EventEditor() {
		// Set up frame
		setTitle("Schedule BillboardEditor");
		setContentPane(main_Panel);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setVisible(true);


		// Pre-fill date & time boxes with current time
		LocalDateTime currentDateTime = LocalDateTime.now();
		startTime_TextField.setText(timeFormatter.format(new Date()));
		startDate_TextField.setText(currentDateTime.format(dateFormatter));

		// Read the list of available billboards and add them to the dropdown box
		ArrayList<String> allBillboardNames;
		try {
			allBillboardNames = (ArrayList<String>)ControlPanel.get().requestSender.SendData(EndpointType.listBillboards, null).getData();
			for (String billboard : allBillboardNames) {
				billboardSelector_ComboBox.addItem(billboard);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}



		ok_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Event event = new Event();

				try { // Parse the start date and time
					// 86400000 is number of seconds in day (86400) * milliseconds in second (1000)
					// This converts epoch days to epoch milliseconds
					event.startTime += 86400000 * LocalDate.parse(startDate_TextField.getText(), dateFormatter).toEpochDay();
					// Add on the hours and minutes, and we're good to go
					event.startTime += timeFormatter.parse(startTime_TextField.getText()).getTime();
				} catch (DateTimeException | ParseException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				// 60 converts minutes to seconds, 1000 converts seconds to milliseconds
				event.setDuration(60 * 1000 * (int)duration_Spinner.getValue());

				// Get the specified billboard name from the drop down box
				event.billboardName = (String)billboardSelector_ComboBox.getSelectedItem();

				// Get the author from the currently logged in user
				event.author = ControlPanel.get().requestSender.getToken().getUser();

				System.out.println(event.toString());

				try {
					ControlPanel.get().requestSender.SendData(EndpointType.addEvents, event);
				} catch (IOException | ClassNotFoundException ex) {
					ex.printStackTrace();
					return;
				}

				ControlPanel.get().refreshEvents();
				dispose();
			}
		});
		cancel_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		billboardSelector_ComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setOkButtonEnabled();
			}
		});
	}

	/**
	 * Call to check if all of the inputs are good.
	 * If they are, enable the ok button
	 * If they are not, disable the ok button.
	 */
	private void setOkButtonEnabled() {
		boolean validBillboardSelected = billboardSelector_ComboBox.getSelectedIndex() != 0;

		ok_Button.setEnabled(validBillboardSelected);
	}
}
