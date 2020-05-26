package Shared.Schedule;

/**
 * An event is a single billboard time allocation that exists in the Schedule.
 * You can treat startTime as a unique identifier, as no two events should start at the same time
 *
 * If you're looking for the more complex behaviour, take a look at the Schedule class.
 *
 * @author Lucas Maldonado n10534342
 */
public class Event {

	// Times are measured in milliseconds since unix epoch
	public long startTime;
	public long endTime;

	public int billboardID; // Reference to the billboard to display
	public int creatorID; // The user that created this event


	// Constructor
	public Event(long inStartTime, long inEndTime, int inBillboardID, int inCreatorID) {
		startTime = inStartTime;
		endTime = inEndTime;
		billboardID = inBillboardID;
		creatorID = inCreatorID;
	}


	/** Gets how long this billboard will run for */
	public long getDuration() {
		return endTime - startTime;
	}


	/** Sets endTime so that Duration will be this amount */
	public void setEndTime(long duration) {
		endTime = startTime + duration;
	}


	/** Check if this event conflicts with another given time slot */
	public boolean overlapsWith(long testStartTime, long testEndTime) {
		// If this event is earlier
		if (startTime < testStartTime) {
			return endTime > testStartTime;
		}

		// If this event is later
		if (startTime > testStartTime) {
			return testEndTime > startTime;
		}

		// If we're here it means both startTimes are identical, so there is an overlap
		return true;
	}

	/** Overloaded to support passing an entire event for convenience */
	public boolean overlapsWith(Event otherEvent) {
		return overlapsWith(otherEvent.startTime, otherEvent.endTime);
	}
}
