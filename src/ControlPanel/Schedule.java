package ControlPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import Server.Actions.ActionType;
import Shared.Credentials;
import Shared.Network.RequestSender;
import Shared.Network.Response;
import Shared.Schedule.Event;

public class Schedule {
	private JPanel Schedule;
	private JTable table1;
	private JButton btnDelete;
	private JButton btnOK;
	private JButton btnSchedule;
	private static JFrame scheduleFrame;

	public JFrame getScheduleFrame() {
		return scheduleFrame;
	}

	public static DefaultTableModel tableModel;



	public Schedule() {
		/**
		 * Action listener for OK button to close window
		 *
		 * @author Callum McNeilage - n10482652
		 */
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getScheduleFrame().dispose();
			}
		});
		/**
		 * Action Listener for Schedule button
		 *
		 * @author Callum McNeilage - n10482652
		 */
		btnSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				times.main(null);
			}
		});

		/**
		 * Queries the server for billboard information and displays in table
		 *
		 * @author Callum McNeilage - n10482652
		 */
		//Queries server to return billboard schedule
		ArrayList<Event> list = null;
		try {
			RequestSender requestSender = new RequestSender("localhost", 9977);
			Credentials credentials = new Credentials("username1234", "password1234");
			requestSender.login(credentials);

			Response response = requestSender.SendData(ActionType.getEvents, null);

			list = (ArrayList<Event>) response.getData();
		}

		catch (NullPointerException | ClassNotFoundException | IOException e) {
			System.out.println(e);
			System.out.println("No billboards to show");
		}

		//Data to be displayed in the JTable
		DefaultTableModel model = (DefaultTableModel) table1.getModel();
		model.addColumn("Billboard");
		model.addColumn("Start Time");

		for (int i = 0; i < list.size(); i++) {
			Object[] row = new Object[2];
			row[0] = Integer.toString(list.get(i).billboardID);
			row[1] = DateFormat(list.get(i).startTime);

			model.addRow(row);
		}
	}

	/**
	 * Takes a date formatted as a long and converts it to Simple Date Format to display in schedule
	 *
	 * @author Callum McNeilage - n10482652
	 * @param unix date formatted as a long
	 * @return Date formatted as yyyy-MM-dd HH:mm:ss z
	 */
	private String DateFormat(long unix) {
		Date date = new Date(unix*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	/**
	 * Loads the schedule window
	 *
	 * @author Callum McNeilage - n10482652
	 * @param args
	 */
	public static void main(String[] args) {
		// Won't compile without the exceptions unhandled
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
			// handle exception
		}

		// Create and setup Schedule window
		scheduleFrame = new JFrame("Billboard Schedule");
		scheduleFrame.setContentPane(new Schedule().Schedule);
		scheduleFrame.setDefaultCloseOperation(scheduleFrame.HIDE_ON_CLOSE);
		scheduleFrame.pack();
		scheduleFrame.setVisible(true);
	}
}
