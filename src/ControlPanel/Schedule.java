package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Schedule {
	private JPanel Schedule;
	private JTable table1;
	private JButton btnDelete;
	private JButton btnOK;
	private static JFrame scheduleFrame;

	public JFrame getScheduleFrame() {
		return scheduleFrame;
	}


	public Schedule() {
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getScheduleFrame().dispose();
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

		// Create and setup Schedule window
		scheduleFrame = new JFrame("Billboard Schedule");
		scheduleFrame.setContentPane(new Schedule().Schedule);
		scheduleFrame.setDefaultCloseOperation(scheduleFrame.HIDE_ON_CLOSE);
		scheduleFrame.pack();
		scheduleFrame.setVisible(true);
	}
}
