package Shared.Schedule;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

	public String billboardName; // Reference to the billboard to display
	public String author; // The user that created this event


	/** Constructor */
	public Event(long inStartTime, long inEndTime, String inBillboardName, String inAuthor) {
		startTime = inStartTime;
		endTime = inEndTime;
		billboardName = inBillboardName;
		author = inAuthor;
	}

	/** Constructor that inits everything to zero */
	public Event() {
		startTime = 0;
		endTime = 0;
		billboardName = "";
		author = "";
	}

	/** Constructor for quickly creating an event for debugging */
	public Event(long start, long end) {
		startTime = start;
		endTime = end;
		billboardName = "test";
		Random r = new Random(System.currentTimeMillis());
		author = "Test Event " + (r.nextInt() % 100);
	}


	/** Gets how long this billboard will run for */
	public long getDuration() {
		return endTime - startTime;
	}


	/** Sets endTime so that Duration will be this amount */
	public void setDuration(long duration) {
		endTime = startTime + duration;
	}


	/**
	 * Used to check if this event has no info in it, like when the scheduler has no billboard to display.
	 * An event is blank when all values are 0 or equivalent.
	 * @returns true if this Event is blank, false otherwise.
	 */
	public boolean isBlank() {
		return startTime == 0
			&& endTime == 0
			&& billboardName == ""
			&& author == "";
	}

	public String toString() {
		return String.format("Event for billboard '%s':\n" +
			"\tStarts at %s\n" +
			"\tLasts for %d minutes\n" +
			"\tCreated by '%s'\n",
			billboardName,
			new SimpleDateFormat("h:mm aa dd-MM-yyyy").format(new Date(startTime)),
			getDuration() / (60 * 1000), author);
	}
}
