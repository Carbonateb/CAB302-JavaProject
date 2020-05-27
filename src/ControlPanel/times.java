package ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class times {
	private JPanel timesWindow;
	private JTextField txtStart;
	private JTextField txtEnd;
	private JButton btnCancel;
	private JButton btnOk;
	public static JFrame timesFrame;

	public times() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
