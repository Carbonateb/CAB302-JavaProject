package Shared.Schedule;

/**
 * An event is a single billboard time allocation that exists in the Schedule.
 * This is a data only class, treat it like a struct.
 *
 * If you're looking for the more complex behaviour, take a look at the Schedule class.
 *
 * @author Lucas Maldonado n10534342
 */
public class Event {
	long startTime;
	long endTime;
	int billboardID; // Reference to the billboard to display
	int creatorID; // The user that created this event
}
