package Shared.Schedule;

import java.io.Serializable;

/**
 * An event is a single billboard time allocation that exists in the Schedule.
 * You can treat startTime as a unique identifier, as no two events should start at the same time
 *
 * If you're looking for the more complex behaviour, take a look at the Schedule class.
 *
 * @author Lucas Maldonado n10534342
 */
public class Event implements Serializable {

	// Times are measured in milliseconds since unix epoch
	public long startTime;
	public long endTime;

	public int billboardID; // Reference to the billboard to display
	public String author; // The user that created this event


	/** Constructor */
	public Event(long inStartTime, long inEndTime, int inBillboardID, String inAuthor) {
		startTime = inStartTime;
		endTime = inEndTime;
		billboardID = inBillboardID;
		author = inAuthor;
	}

	/** Constructor for quickly creating an event for debugging */
	public Event(long start, long end) {
		startTime = start;
		endTime = end;
		billboardID = 0;
		author = "";
	}


	/** Gets how long this billboard will run for */
	public long getDuration() {
		return endTime - startTime;
	}


	/** Sets endTime so that Duration will be this amount */
	public void setEndTime(long duration) {
		endTime = startTime + duration;
	}


	/**
	 * Used to check if this event has no info in it, like when the scheduler has no billboard to display.
	 * An event is blank when all values are 0 or equivalent.
	 * @return true if this Event is blank, false otherwise.
	 */
	public boolean isBlank() {
		return startTime == 0
			&& endTime == 0
			&& billboardID == 0
			&& author == "";
	}
}
