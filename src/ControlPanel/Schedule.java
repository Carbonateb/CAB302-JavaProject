package ControlPanel;

import Server.Endpoints.EndpointType;
import Shared.Network.Response;
import Shared.Schedule.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
		 * Endpoint listener for OK button to close window
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
		 * Endpoint Listener for Schedule button
		 *
		 * @author Callum McNeilage - n10482652
		 */
		btnSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EventCreator();
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
			Response response = ControlPanel.get().requestSender.SendData(EndpointType.getEvents, null);

			// Check if the server replied with an error
			if (response.getStatus().equals("error")) {
				if (response.getData().equals("Invalid token")) {
					System.out.println("Your session token is invalid (probably expired), try logging in again");
				}
				else {
					System.out.println("Server replied with an error: " + response.getData().toString());
				}
				return;
			}

			if (response.getData() instanceof ArrayList) {
				list = (ArrayList<Event>) response.getData();
			} else if(response.getData().toString().equals("")){
				System.out.println("No billboards to show");
			}
			else {
				System.out.printf("Server replied with unexpected data: '%s'\n", response.getData().toString());
				return;
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("There was an issue when retrieving billboards");
		}

		//Data to be displayed in the JTable
		DefaultTableModel model = (DefaultTableModel) table1.getModel();
		model.addColumn("Billboard");
		model.addColumn("Start Time");

		for (int i = 0; i < list.size(); i++) {
			Object[] row = new Object[2];
			row[0] = list.get(i).billboardName;
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
		return sdf.format(date);
	}

	/**
	 * Loads the schedule window
	 *
	 * @author Callum McNeilage - n10482652
	 * @param args
	 */
	public static void main(String[] args) {
		// Create and setup Schedule window
		scheduleFrame = new JFrame("Billboard Schedule");
		scheduleFrame.setContentPane(new Schedule().Schedule);
		scheduleFrame.setDefaultCloseOperation(scheduleFrame.HIDE_ON_CLOSE);
		scheduleFrame.pack();
		scheduleFrame.setVisible(true);
	}
}
