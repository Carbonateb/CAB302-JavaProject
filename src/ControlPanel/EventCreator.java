package ControlPanel;

import Shared.Schedule.Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
	public EventCreator() {
		// Set up frame
		setTitle("Schedule Billboard");
		setContentPane(main_Panel);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		setVisible(true);


		// Prefill date & time boxes with current time
		LocalDateTime currentDateTime = LocalDateTime.now();
		startTime_TextField.setText(timeFormatter.format(new Date()));
		startDate_TextField.setText(currentDateTime.format(dateFormatter));



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
				event.billboardName = billboardSelector_ComboBox.getName();

				// Get the author from the currently logged in user
				event.author = ControlPanel.get().requestSender.getToken().getUser();

				System.out.println(event.toString());
			}
		});
		cancel_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
