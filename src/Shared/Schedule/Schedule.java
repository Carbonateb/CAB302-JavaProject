package Shared.Schedule;

import java.util.ArrayList;

/**
 * The Schedule manages a collection of Events. You can add billboards to the Schedule by adding Events.
 * Complex behaviour like looping events is also supported here.
 *
 * @author Lucas Maldonado n10534342
 */
public class Schedule {

	/**
	 * The events that are currently active. Normally only one thing is here, but multiple are allowed to support
	 * layering of events, as specified in the requirements.
	 */
	private ArrayList<Event> activeEvents = new ArrayList<Event>();

	/**
	 * The events that have been scheduled but are not being displayed at the moment. When it's a billboard's turn to be
	 * displayed, it will be moved into the activeEvents array.
	 */
	private ArrayList<Event> upcomingEvents = new ArrayList<Event>();


	// Constructor
	public Schedule() {
	}


	/**
	 * Moves billboards in upcomingEvents to activeEvents if they are to be displayed now.
	 */
	private void populateActiveEvents() {
		ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();

		// Check which events are active now, copy them to activeEvents and mark them for death
		for (int i = 0; i < upcomingEvents.size(); ++i) {
			if (upcomingEvents.get(i).startTime >= currentTime()) {
				indicesToRemove.add(i);
				activeEvents.add(upcomingEvents.get(i));
			}
		}

		// Clear the events that got marked
		for (int i : indicesToRemove) {
			upcomingEvents.remove(i);
		}
	}


	/**
	 * Removes passed events from activeEvents, keeping it nice and tidy.
	 */
	private void cleanupActiveEvents() {
		ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();

		// Check which events have passed, mark them for deletion
		for (int i = 0; i < activeEvents.size(); ++i) {
			if (activeEvents.get(i).endTime < currentTime()) {
				indicesToRemove.add(i);
			}
		}

		// Delete the events that got marked
		for (int i : indicesToRemove) {
			activeEvents.remove(i);
		}
	}


	private long currentTime() {
		return System.currentTimeMillis();
	}

}
