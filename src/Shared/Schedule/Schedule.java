package Shared.Schedule;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Schedule manages a collection of Events. You can add billboards to the Schedule by adding Events.
 * Complex behaviour like looping events is also supported here.
 *
 * @author Lucas Maldonado n10534342
 */
public class Schedule implements Serializable {

	/**
	 * The events that are currently active. Normally only one thing is here, but multiple are allowed to support
	 * layering of events, as specified in the requirements.
	 */
	public ArrayList<Event> activeEvents = new ArrayList<Event>();

	/**
	 * The events that have been scheduled but are not being displayed at the moment. When it's a billboard's turn to be
	 * displayed, it will be moved into the activeEvents array.
	 */
	public ArrayList<Event> upcomingEvents = new ArrayList<Event>();


	// Constructor
	public Schedule() {
	}


	/**
	 * Figures out the event to display, from the list of billboards that can be displayed right now.
	 * Newer events have priority over older ones.
	 * If there are no events to display, will return a blank event. Use event.isBlank to check
	 */
	public Event getEvent() {
		populateActiveEvents();
		cleanupActiveEvents();
		if (activeEvents.size() == 0) {
			// Return a blank event if there is none to display
			return new Event(0, 0, 0, "");
		}

		// TODO unsure if we need a priority system, for now just uses age where newer ones get displayed over old ones
		return activeEvents.get(activeEvents.size() - 1); // Gets the last element in the array
	}


	/**
	 * Adds an event to the schedule. Supports hot-swapping active events, so you can add an event that is currently
	 * active.
	 * @param newEvent the event to add
	 */
	public void scheduleEvent(Event newEvent) {
		System.out.println("Added new event");
		upcomingEvents.add(newEvent);
		populateActiveEvents();
		cleanupActiveEvents();
	}


	/**
	 * Removes an event from the schedule.
	 * @param e the event to remove
	 * @param removeActive if false, will not affect currently active events.
	 * @returns true if the event was found and deleted
	 */
	public boolean removeEvent(Event e, boolean removeActive) {
		if (removeActive) {
			return upcomingEvents.remove(e) || activeEvents.remove(e);
		} else {
			return upcomingEvents.remove(e);
		}
	}


	/**
	 * Moves billboards in upcomingEvents to activeEvents if they are to be displayed now.
	 */
	private void populateActiveEvents() {
		ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();

		// Check which events are active now, copy them to activeEvents and mark them for death
		for (int i = 0; i < upcomingEvents.size(); ++i) {
			if (currentTime() >= upcomingEvents.get(i).startTime) {
				indicesToRemove.add(i);
				activeEvents.add(upcomingEvents.get(i));
				System.out.printf("Moved an event to Active Events: start time is %d, current time is %d (%dms late)\n", upcomingEvents.get(i).startTime, currentTime(), currentTime() - upcomingEvents.get(i).startTime);
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
			if (currentTime() > activeEvents.get(i).endTime) {
				indicesToRemove.add(i);
				System.out.printf("Deleted an old event: end time was %d, current time is %d (finished %dms ago)\n", activeEvents.get(i).endTime, currentTime(), currentTime() - activeEvents.get(i).endTime);
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
