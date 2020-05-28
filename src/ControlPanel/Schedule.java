package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Server.Actions.ActionType;
import Server.Server;
import Server.dbServer;
import Shared.Credentials;
import Shared.Network.RequestSender;
import Shared.Network.Response;
import Shared.Schedule.Event;

import static Server.dbServer.*;

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


	/**
	 * Action listener for OK button to close window
	 *
	 * @author Callum McNeilage - n10482652
	 */
	public Schedule() {
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getScheduleFrame().dispose();
			}
		});
		btnSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				times.main(null);
			}
		});
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

		//Queries server to return billboard schedule
		try {
			String localhost = "localhost";
			RequestSender requestSender = new RequestSender(localhost, 9977);
			Credentials credentials = new Credentials("username", "password");
			requestSender.login(credentials);

			Response response = requestSender.SendData(ActionType.getEvents, null);
		}

		catch (java.lang.NullPointerException | java.lang.ClassNotFoundException | java.io.IOException e) {
			System.out.println(e);
			System.out.println("No billboards to show");
		}

		// Create and setup Schedule window
		scheduleFrame = new JFrame("Billboard Schedule");
		scheduleFrame.setContentPane(new Schedule().Schedule);
		scheduleFrame.setDefaultCloseOperation(scheduleFrame.HIDE_ON_CLOSE);
		scheduleFrame.pack();
		scheduleFrame.setVisible(true);
	}
}
