package Shared.Schedule;

import java.util.ArrayList;

/**
 * The Schedule manages a collection of Events. You can add billboards to the Schedule by adding Events.
 * Complex behaviour like looping events is also supported here.
 *
 * @author Lucas Maldonado n10534342
 */
public class Schedule {

	/** The main storage container for all of the events */
	private ArrayList<Event> events = new ArrayList<Event>();


	// Constructor
	public Schedule() {
	}


	/** Adds a event to the schedule */
	public boolean addEvent(Event newEvent) {
		if (canFit(newEvent)) {
			events.add(newEvent);
			return true;
		}
		return false;
	}


	/** Removes an event from the schedule. Returns true if successful */
	public boolean removeEvent(long startTime) {
		for (int i = 0; i < events.size(); ++i) {
			if (events.get(i).startTime == startTime) {
				events.remove(i);
				return true;
			}
		}
		return false;
	}


	/** Removes an event from the schedule, given an entire event. Returns true if successful */
	public boolean removeEvent(Event event) {
		return removeEvent(event.startTime);
	}


	/** Checks if a new event can fit in the schedule without any conflicts */
	public boolean canFit(long testStartTime, long testEndTime) {
		for (Event e : events) {
			if (e.overlapsWith(testStartTime, testEndTime)) {
				return false;
			}
		}
		return true;
	}


	/** Checks if a new event can fit in the schedule without any conflicts */
	public boolean canFit(Event contender) {
		return canFit(contender.startTime, contender.endTime);
	}


	/** Gets the billboard that should be displaying now */
	public Event getCurrentEvent() {
		return new Event(0, 0, 0, 0);
	}


	private long getTime() {
		return System.currentTimeMillis();
	}

}
